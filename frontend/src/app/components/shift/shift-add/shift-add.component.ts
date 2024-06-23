import { LiveAnnouncer } from '@angular/cdk/a11y';
import { ENTER, COMMA } from '@angular/cdk/keycodes';
import { Component, ElementRef, ViewChild, inject } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { MatChipInputEvent } from '@angular/material/chips';
import { ActivatedRoute, Router } from '@angular/router';
import { DateTime } from 'luxon';
import { Observable, startWith, map } from 'rxjs';
import { EmployeeDto } from 'src/app/models/employee.model';
import { ShiftRequestDto } from 'src/app/models/shift-request-dto.model';
import { EmployeeService } from 'src/app/services/employee.service';
import { ShiftService } from 'src/app/services/shift.service';

@Component({
  selector: 'app-shift-add',
  templateUrl: './shift-add.component.html',
  styleUrls: ['./shift-add.component.css']
})
export class ShiftAddComponent {
  hide = true;
  addForm = new FormGroup({
    employee: new FormControl(null),
    shiftType: new FormControl("", [
      Validators.required
    ]),
    fromTime: new FormControl(""),
    toTime: new FormControl(""),
    range: new FormGroup({
      fromDate: new FormControl<Date | null>(null, Validators.required),
      toDate: new FormControl<Date | null>(null, Validators.required),
    })
  });

  separatorKeysCodes: number[] = [ENTER, COMMA];
  filteredEmployees: Observable<EmployeeDto[]>;
  employees: EmployeeDto[] = [];
  allEmployees: EmployeeDto[] = [];
  fruitCtrl = new FormControl('');
  minDate: Date;
  maxDate: Date;

  @ViewChild('fruitInput') fruitInput: ElementRef<HTMLInputElement>;

  announcer = inject(LiveAnnouncer);

  constructor(private shiftService: ShiftService,
    private employeeService: EmployeeService,
    private router: Router, private route: ActivatedRoute) {
    this.filteredEmployees = this.fruitCtrl.valueChanges.pipe(
      startWith(null),
      map((fruit: string | null) => (typeof fruit === 'string' ? this._filter(fruit) : this.allEmployees?.slice())),
    );
  }

  ngOnInit(): void {
    const currentYear = new Date().getFullYear();
    const currentMonth = new Date().getMonth();
    const currentDate = new Date().getDate();
    this.minDate = new Date(currentYear, currentMonth, currentDate);
    this.maxDate = new Date(currentYear + 1, currentMonth, currentDate);
    this.employeeService.getAllEmployees().subscribe(data => {
      this.allEmployees = data;
    });
  }

  add(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();

    if (value) {
      this.employees.push();
    }

    event.chipInput!.clear();

    this.fruitCtrl.setValue(null);
  }

  remove(fruit: EmployeeDto): void {
    const index = this.employees.indexOf(fruit);

    if (index >= 0) {
      this.employees.splice(index, 1);

      this.announcer.announce(`Removed ${fruit}`);
    }
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    this.employees.push(event.option.value);
    this.fruitInput.nativeElement.value = '';
    this.fruitCtrl.setValue(null);
  }

  private _filter(value: string): EmployeeDto[] {
    const filterValue = value.toLowerCase();

    return this.allEmployees.filter(employee => employee.name.toLowerCase().includes(filterValue) && !this.employees.some(x => x.id === employee.id));
  }
  ////////////////
  formControlItem: FormControl = new FormControl('');
  maxTime: DateTime = DateTime.local().set({
    hour: 16,
  });
  minTime: DateTime = DateTime.local().set({
    hour: 14,
  });
  required: boolean = !1;

  @ViewChild('from') timepicker: any;

  /**
   * Lets the user click on the icon in the input.
   */
  openFromIcon(timepicker: { open: () => void }) {
    if (!this.formControlItem.disabled) {
      timepicker.open();
    }
  }

  /**
   * Function to clear FormControl's value, called from the HTML template using the clear button
   *
   * @param $event - The Event's data object
   */
  onClear($event: Event) {
    this.formControlItem.setValue(null);
  }

  onSubmit() {
    var addDto: ShiftRequestDto = new ShiftRequestDto();
    var dateFrom = this.addForm.controls.range.controls.fromDate.value;
    var dateTo = this.addForm.controls.range.controls.toDate.value;

    if (dateFrom) {
      addDto.periodFrom = new Date(dateFrom.getFullYear(), dateFrom.getMonth(), dateFrom.getDate(), 0, 0, 0);
    }
    if (dateTo) {
      addDto.periodTo = new Date(dateTo.getFullYear(), dateTo.getMonth(), dateTo.getDate(), 23, 59, 59);
    }

    //   // mislam nema potreba od ova
    // if (dateFrom) {
    //  addDto.startDateTime = moment(dateFrom).local().toDate();
    // }
    // if (dateTo) {
    //   addDto.endDateTime = moment(dateTo).local().toDate();;
    // }

    if (this.addForm.controls.shiftType.value)
      addDto.shiftType = this.addForm.controls.shiftType.value;

    switch (addDto.shiftType) {
      case "FIRST":
        addDto.shiftStart = new Date(addDto.periodFrom.getFullYear(), addDto.periodFrom.getMonth(), addDto.periodFrom.getDate(), 8, 0, 0);
        addDto.shiftEnd = new Date(addDto.periodTo.getFullYear(), addDto.periodTo.getMonth(), addDto.periodTo.getDate(), 16, 0, 0);
        break;
      case "SECOND":
        addDto.shiftStart = new Date(addDto.periodFrom.getFullYear(), addDto.periodFrom.getMonth(), addDto.periodFrom.getDate(), 14, 0, 0);
        addDto.shiftEnd = new Date(addDto.periodTo.getFullYear(), addDto.periodTo.getMonth(), addDto.periodTo.getDate(), 22, 0, 0);
        break;
      case "CUSTOM":
        let shift = this.addForm.controls.fromTime.value;
        if (shift != null) {
          let timeParts: string[] = shift.split(":");
          // Parse the hours and minutes as numbers
          let hours: number = parseInt(timeParts[0], 10);
          let minutes: number = parseInt(timeParts[1], 10);
          addDto.shiftStart = new Date(addDto.periodFrom.getFullYear(), addDto.periodFrom.getMonth(), addDto.periodFrom.getDate(), hours, minutes, 0);

        }
        shift = this.addForm.controls.toTime.value;
        if (shift != null) {

          let timeParts: string[] = shift.split(":");
          // Parse the hours and minutes as numbers
          let hours: number = parseInt(timeParts[0], 10);
          let minutes: number = parseInt(timeParts[1], 10);
          addDto.shiftEnd = new Date(addDto.periodTo.getFullYear(), addDto.periodTo.getMonth(), addDto.periodTo.getDate(), minutes, hours, 0);

        }
    }
    if (this.employees.length > 0) {
      addDto.employees = this.employees.map(service => service.id);
    }
    console.log(addDto);
    this.shiftService.createShift(addDto).subscribe(response => {
      this.router.navigateByUrl('/shifts');
    });
  }
}