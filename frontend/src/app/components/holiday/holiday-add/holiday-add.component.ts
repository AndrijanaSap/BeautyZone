import { LiveAnnouncer } from '@angular/cdk/a11y';
import { ENTER, COMMA } from '@angular/cdk/keycodes';
import { Component, ElementRef, ViewChild, inject } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { MatChipInputEvent } from '@angular/material/chips';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, startWith, map } from 'rxjs';
import { CategoryWithServicesDto } from 'src/app/models/category-with-services.model';
import { EmployeeDto } from 'src/app/models/employee.model';
import { HolidayRequestDto } from 'src/app/models/holiday-request-dto.model';
import { ServiceDto } from 'src/app/models/service.model';
import { UpdateCategoryWithServicesDto } from 'src/app/models/update-category-with-services.model';
import { UpdateServiceWithEmployeesDto } from 'src/app/models/update-service-with-employees.model';
import { CategoryService } from 'src/app/services/category.service';
import { EmployeeService } from 'src/app/services/employee.service';
import { HolidayService } from 'src/app/services/holiday.service';
import * as moment from 'moment';

@Component({
  selector: 'app-holiday-add',
  templateUrl: './holiday-add.component.html',
  styleUrls: ['./holiday-add.component.css']
})
export class HolidayAddComponent  {
  hide = true;
  addForm = new FormGroup({
    id: new FormControl(0, [
      Validators.required
    ]),
    name: new FormControl(""),
    holidayType: new FormControl("", [
      Validators.required
    ]),
    employee: new FormControl(null),
    range: new FormGroup({
      start: new FormControl<Date | null>(null, Validators.required),
      end: new FormControl<Date | null>(null, Validators.required),
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

  constructor(private holidayService: HolidayService,
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


  onSubmit() {
    var addDto: HolidayRequestDto = new HolidayRequestDto();
    var dateFrom = this.addForm.controls.range.controls.start.value;
    var dateTo = this.addForm.controls.range.controls.end.value;

    if (dateFrom) {
      addDto.startDateTime = new Date(dateFrom.getFullYear(), dateFrom.getMonth(), dateFrom.getDate(), 0, 0, 0);
    }
    if (dateTo) {
      addDto.endDateTime = new Date(dateTo.getFullYear(), dateTo.getMonth(), dateTo.getDate(), 23, 59, 59);
    }

    //   // mislam nema potreba od ova
    // if (dateFrom) {
    //  addDto.startDateTime = moment(dateFrom).local().toDate();
    // }
    // if (dateTo) {
    //   addDto.endDateTime = moment(dateTo).local().toDate();;
    // }

    if (this.addForm.controls.id.value)
      addDto.id = this.addForm.controls.id.value;

    if (this.addForm.controls.holidayType.value)
      addDto.holidayType = this.addForm.controls.holidayType.value;

    if (this.addForm.controls.name.value)
      addDto.name = this.addForm.controls.name.value;
  
    if (this.employees.length > 0) {
      addDto.employees = this.employees.map(service => service.id);
    }
    this.holidayService.createHoliday(addDto).subscribe(response => {
      this.router.navigateByUrl('/holidays');
    });
  }

}



