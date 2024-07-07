import {
  ChangeDetectionStrategy,
  Component,
  ViewEncapsulation,
  ChangeDetectorRef,
} from '@angular/core';
import { Subject } from 'rxjs';
import {
  CalendarEvent,
  CalendarEventTimesChangedEvent,
  CalendarView,
} from 'angular-calendar';
import { colors } from './../../../utils/colors';
import {
  addDays,
  addHours,
  addMinutes,
  isSameDay,
  setDay,
  startOfDay,
  subDays,
  subSeconds,
} from 'date-fns';
import { AppointmentService } from 'src/app/services/appointment.service';
import { ActivatedRoute } from '@angular/router';
import { ShiftService } from 'src/app/services/shift.service';
import { EmployeeDto } from 'src/app/models/employee.model';
import { EmployeeService } from 'src/app/services/employee.service';

@Component({
  selector: 'app-employee-appointment-list',
  templateUrl: './employee-appointment-list.component.html',
  styleUrls: ['./employee-appointment-list.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  encapsulation: ViewEncapsulation.None,
})
export class EmployeeAppointmentListComponent {
  view: CalendarView = CalendarView.Week;
  excludeDays: number[] = [0];
  viewDate: Date = new Date();
  events: CalendarEvent[] = [];
  employees: EmployeeDto[];
  employeeId: number | undefined;
  refresh = new Subject<void>();

  constructor(
    private appointmentService: AppointmentService,
    private shiftService: ShiftService,
    private employeeService: EmployeeService,
    private route: ActivatedRoute,
    private cdr: ChangeDetectorRef) {
  }

  ngOnInit(): void {
    this.employeeService.getAllEmployees().subscribe(data => {
      this.employees = data;
      const employeeId = Number(this.route.snapshot.queryParams['employeeId']);
      if (employeeId) {
        this.employeeId = employeeId;
        this.onEmployeeChange();
        // this.employeeId = this.employees.find;
        // this.fetchHolidays();
        // this.fetchAppointments();
      }
    });

  }

  private fetchAppointments(employeeId: number) {
    this.appointmentService.getAllAppointmentByEmployeeId(employeeId.toString()).subscribe(appointments => {
      this.events = this.events.concat(appointments.map(appointment => {
        return {
          id: appointment.id.toString(),
          start: new Date(appointment.appointmentDateTime),
          end: addMinutes(new Date(appointment.appointmentDateTime), appointment.service.durationInMinutes),
          // end: appointment.appointmentDateTime.setMinutes(appointment.appointmentDateTime.getMinutes() +  appointment.service.durationInMinutes),
          title: appointment.service.name + ' - ' +  appointment.name,
          color: colors.blue,
          resizable: {
            beforeStart: true,
            afterEnd: true,
          },
          draggable: true,
        };
      }));
      this.cdr.markForCheck();
    });
    this.shiftService.getAllByEmployeeIdWithHolidays(employeeId.toString()).subscribe(shifts => {
      this.events = this.events.concat(shifts.map(shift => {
        let title = "";
        let color = colors.white;
        if (shift.holiday) {
          title = shift.holiday.name;
          color = colors.green;
        } else {
          title = new Date(shift.shiftStart).getHours().toString().padStart(2, '0')
            + ':' + new Date(shift.shiftStart).getMinutes().toString().padStart(2, '0')
            + ' - ' + new Date(shift.shiftEnd).getHours().toString().padStart(2, '0')
            + ':' + new Date(shift.shiftEnd).getMinutes().toString().padStart(2, '0')
        }
        return {
          id: shift.id.toString(),
          start: new Date(shift.shiftStart),
          title: title,
          color: color,
          allDay: true,
        };
      }));
      this.cdr.markForCheck();
    });
  }

  onEmployeeChange() {
    if (this.employeeId)
      this.fetchAppointments(this.employeeId);
  }

  validateEventTimesChanged = (
    { event, newStart, newEnd, allDay }: CalendarEventTimesChangedEvent,
    addCssClass = true
  ) => {
    if (event.allDay) {
      return true;
    }

    delete event.cssClass;
    if (newEnd) {
      // don't allow dragging or resizing events to different days
      const sameDay = isSameDay(newStart, newEnd);
      if (!sameDay) {
        return false;
      }

      // don't allow dragging events to the same times as other events
      const overlappingEvent = this.events.find((otherEvent) => {
        return (
          otherEvent && otherEvent.end &&
          otherEvent !== event &&
          !otherEvent.allDay &&
          ((otherEvent.start < newStart && newStart < otherEvent.end) ||
            (otherEvent.start < newEnd && newStart < otherEvent.end))
        );
      });

      if (overlappingEvent) {
        if (addCssClass) {
          event.cssClass = 'invalid-position';
        } else {
          return false;
        }
      }

    }


    return true;
  };

  eventTimesChanged(
    eventTimesChangedEvent: CalendarEventTimesChangedEvent
  ): void {
    delete eventTimesChangedEvent.event.cssClass;
    if (this.validateEventTimesChanged(eventTimesChangedEvent, false)) {
      const { event, newStart, newEnd } = eventTimesChangedEvent;
      event.start = newStart;
      event.end = newEnd;
      this.refresh.next();
    }
  }
}
