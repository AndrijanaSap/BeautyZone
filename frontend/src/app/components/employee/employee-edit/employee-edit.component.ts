import { LiveAnnouncer } from '@angular/cdk/a11y';
import { ENTER, COMMA } from '@angular/cdk/keycodes';
import { Component, ElementRef, ViewChild, inject } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { MatChipInputEvent } from '@angular/material/chips';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, startWith, map } from 'rxjs';
import { EmployeeRegisterDto } from 'src/app/models/employee-register-dto.model';
import { EmployeeUpdateDto } from 'src/app/models/employee-update-dto.model';
import { ServiceDto } from 'src/app/models/service.model';
import { UserDto } from 'src/app/models/user.model';
import { EmployeeService } from 'src/app/services/employee.service';
import { ServiceService } from 'src/app/services/service.service';

@Component({
  selector: 'app-employee-edit',
  templateUrl: './employee-edit.component.html',
  styleUrls: ['./employee-edit.component.css']
})
export class EmployeeEditComponent {
  hide = true;
  updateForm = new FormGroup({
    id: new FormControl(0, [
      Validators.required
    ]),
    email: new FormControl("", [
      Validators.required
    ]),
    name: new FormControl("", [
      Validators.required
    ]),
    surname: new FormControl("", [
      Validators.required
    ]),
    phone: new FormControl("", [
      Validators.required
    ]),
  });
  separatorKeysCodes: number[] = [ENTER, COMMA];
  fruitCtrl = new FormControl('');
  filteredServices: Observable<ServiceDto[]>;
  services: ServiceDto[] = [];
  allServices: ServiceDto[] = [];

  @ViewChild('fruitInput') fruitInput: ElementRef<HTMLInputElement>;

  announcer = inject(LiveAnnouncer);
  constructor(private employeeService: EmployeeService, private serviceService: ServiceService, private router: Router, private route: ActivatedRoute) {
    this.filteredServices = this.fruitCtrl.valueChanges.pipe(
      startWith(null),
      map((fruit: string | null) => (typeof fruit === 'string' ? this._filter(fruit) : this.allServices?.slice())),
    );
  }

  ngOnInit(): void {
    const userId = this.route.snapshot.paramMap.get('id');
    if (userId) {
      this.employeeService.getEmployeeById(userId).subscribe(data => {
        if (data.id) this.updateForm.controls.id.setValue(data.id);
        if (data.name) this.updateForm.controls.name.setValue(data.name);
        if (data.surname) this.updateForm.controls.surname.setValue(data.surname);
        if (data.email) this.updateForm.controls.email.setValue(data.email);
        if (data.phone) this.updateForm.controls.phone.setValue(data.phone);
        if (data.services) this.services = data.services;
      });
    }
    this.serviceService.getAllServices().subscribe(data => {
      this.allServices = data;
    });
  }

  add(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();

    if (value) {
      this.services.push();
    }

    event.chipInput!.clear();

    this.fruitCtrl.setValue(null);
  }

  remove(fruit: ServiceDto): void {
    const index = this.services.indexOf(fruit);

    if (index >= 0) {
      this.services.splice(index, 1);

      this.announcer.announce(`Removed ${fruit}`);
    }
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    this.services.push(event.option.value);
    this.fruitInput.nativeElement.value = '';
    this.fruitCtrl.setValue(null);
  }

  private _filter(value: string): ServiceDto[] {
    const filterValue = value.toLowerCase();

    return this.allServices.filter(service => service.name.toLowerCase().includes(filterValue) && !this.services.some(x => x.id === service.id));
  }

  onSubmit() {
    var userDto: EmployeeUpdateDto = new EmployeeUpdateDto();

    if (this.updateForm.controls.id.value)
      userDto.id = this.updateForm.controls.id.value;

    if (this.updateForm.controls.email.value)
      userDto.email = this.updateForm.controls.email.value;

    if (this.updateForm.controls.name.value)
      userDto.name = this.updateForm.controls.name.value;

    if (this.updateForm.controls.surname.value)
      userDto.surname = this.updateForm.controls.surname.value;

    if (this.updateForm.controls.phone.value)
      userDto.phone = this.updateForm.controls.phone.value;

      userDto.role = "EMPLOYEE";

      if(this.services.length > 0){
        userDto.services = this.services.map(service => service.id);
      }
  
    this.employeeService.updateEmployee(userDto).subscribe(response => {
      this.router.navigateByUrl('/employees');
    });
  }
}
