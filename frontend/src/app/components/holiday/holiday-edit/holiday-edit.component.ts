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

@Component({
  selector: 'app-holiday-edit',
  templateUrl: './holiday-edit.component.html',
  styleUrls: ['./holiday-edit.component.css']
})
export class HolidayEditComponent {
  hide = true;
  updateForm = new FormGroup({
    id: new FormControl(0, [
      Validators.required
    ]),
    name: new FormControl(""),
    holidayType: new FormControl("", [
      Validators.required
    ]),
    employee: new FormControl({value:null, disabled: true}),
    range: new FormGroup({
      start: new FormControl<Date | null>(null, Validators.required),
      end: new FormControl<Date | null>(null, Validators.required),
    })
  });

  separatorKeysCodes: number[] = [ENTER, COMMA];
  minDate: Date;
  maxDate: Date;
  employee: EmployeeDto;

  constructor(private holidayService: HolidayService,
    private employeeService: EmployeeService,
    private router: Router, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    const currentYear = new Date().getFullYear();
    const currentMonth = new Date().getMonth();
    const currentDate = new Date().getDate();
    this.minDate = new Date(currentYear, currentMonth, currentDate);
    this.maxDate = new Date(currentYear + 1, currentMonth, currentDate);

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.holidayService.getHolidayById(id).subscribe(data => {
        if (data.id) this.updateForm.controls.id.setValue(data.id);
        if (data.name) this.updateForm.controls.name.setValue(data.name);
        if (data.holidayType) this.updateForm.controls.holidayType.setValue(data.holidayType);
        if (data.employee) this.employee=data.employee;
        if (data.startDateTime)
          { 
            this.updateForm.controls.range.controls.start.setValue(new Date(data.startDateTime));
          }
          if (data.endDateTime)
            { 
              this.updateForm.controls.range.controls.end.setValue(new Date(data.endDateTime));
            }
            this.updateForm.controls.range.updateValueAndValidity();
      });
    }
  }

  onSubmit() {
    var updateDto: HolidayRequestDto = new HolidayRequestDto();
    var dateFrom = this.updateForm.controls.range.controls.start.value;
    var dateTo = this.updateForm.controls.range.controls.end.value;

    if (dateFrom) {
      updateDto.startDateTime = new Date(dateFrom.getFullYear(), dateFrom.getMonth(), dateFrom.getDate(), 0, 0, 0);
    }
    if (dateTo) {
      updateDto.endDateTime = new Date(dateTo.getFullYear(), dateTo.getMonth(), dateTo.getDate(), 23, 59, 59);
    }

    if (this.updateForm.controls.id.value)
      updateDto.id = this.updateForm.controls.id.value;

    if (this.updateForm.controls.holidayType.value)
      updateDto.holidayType = this.updateForm.controls.holidayType.value;

    if (this.updateForm.controls.name.value)
      updateDto.name = this.updateForm.controls.name.value;

    if (this.employee)
      updateDto.employees = [this.employee.id];


    this.holidayService.updateHoliday(updateDto).subscribe(response => {
      this.router.navigateByUrl('/holidays');
    });
  }

}



