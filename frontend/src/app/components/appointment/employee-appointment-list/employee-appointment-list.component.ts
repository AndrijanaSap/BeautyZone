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
  // events: CalendarEvent[] = [
  //   {
  //     start: addHours(startOfDay(setDay(new Date(), 3)), 2),
  //     end: subSeconds(addHours(startOfDay(setDay(new Date(), 3)), 3), 1),
  //     title: 'An short event',
  //     color: colors.yellow,
  //     resizable: {
  //       beforeStart: true,
  //       afterEnd: true,
  //     },
  //     draggable: true,
  //   },
  //   {
  //     start: addHours(startOfDay(setDay(new Date(), 3)), 5),
  //     end: subSeconds(addHours(startOfDay(setDay(new Date(), 3)), 10), 1),
  //     title: 'A draggable and resizable event',
  //     color: colors.yellow,
  //     resizable: {
  //       beforeStart: true,
  //       afterEnd: true,
  //     },
  //     draggable: true,
  //   },
  // ];


  refresh = new Subject<void>();

  constructor(private appointmentService: AppointmentService, private route: ActivatedRoute, private cdr: ChangeDetectorRef) {
  }

  ngOnInit(): void {
    this.fetchHolidays();
    this.fetchAppointments();
  }

  private fetchAppointments() {
    const employeeId = Number(this.route.snapshot.queryParams['employeeId']);
    if (employeeId)
      this.appointmentService.getAllAppointmentByEmployeeId(employeeId.toString()).subscribe(appointments => {
        this.events = this.events.concat(appointments.map(appointment => {
          return {
            id: appointment.id.toString(),
            start: new Date(appointment.appointmentDateTime),
            end: addMinutes(new Date(appointment.appointmentDateTime), appointment.service.durationInMinutes),
            // end: appointment.appointmentDateTime.setMinutes(appointment.appointmentDateTime.getMinutes() +  appointment.service.durationInMinutes),
            title: appointment.client.name + ' - ' + appointment.service.name,
            color: colors.yellow,
            resizable: {
              beforeStart: true,
              afterEnd: true,
            },
            draggable: true,
          };
        }));
        this.cdr.markForCheck();
      });
  }

  private fetchHolidays() {
    this.appointmentService.getHolidays().subscribe(holidays => {
      this.events = this.events.concat(holidays.map(holiday => {
          return {
            id: "holiday",
            start: new Date(holiday.date),
            title: holiday.localName,
            allDay: true,
            color: colors.green,
          };
        }));
        this.cdr.markForCheck();
      });

  }

  validateEventTimesChanged = (
    { event, newStart, newEnd, allDay }: CalendarEventTimesChangedEvent,
    addCssClass = true
  ) => {
    if (event.allDay) {
      return true;
    }

    delete event.cssClass;
    if (newEnd){
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
