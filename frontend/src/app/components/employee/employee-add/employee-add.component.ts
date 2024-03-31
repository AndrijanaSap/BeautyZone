import { LiveAnnouncer } from '@angular/cdk/a11y';
import { Component, ElementRef, ViewChild, inject } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { MatChipInputEvent } from '@angular/material/chips';
import { Router } from '@angular/router';
import { Observable, startWith, map } from 'rxjs';
import { EmployeeRegisterDto } from 'src/app/models/employee-register-dto.model';
import { EmployeeService } from 'src/app/services/employee.service';
import {COMMA, ENTER} from '@angular/cdk/keycodes';
import {MatAutocompleteSelectedEvent, MatAutocompleteModule} from '@angular/material/autocomplete';
import { ServiceService } from 'src/app/services/service.service';
import { ServiceDto } from 'src/app/models/service.model';
import { EmployeeDto } from 'src/app/models/employee.model';

@Component({
  selector: 'app-employee-add',
  templateUrl: './employee-add.component.html',
  styleUrls: ['./employee-add.component.css']
})
export class EmployeeAddComponent {
  hide = true;
  registerForm = new FormGroup({
    email: new FormControl("", [
      Validators.required
    ]),
    password: new FormControl("", [
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
  services: ServiceDto[]=[];
  allServices: ServiceDto[]=[];

  @ViewChild('fruitInput') fruitInput: ElementRef<HTMLInputElement>;

  announcer = inject(LiveAnnouncer);

  constructor(private employeeService: EmployeeService, private serviceService: ServiceService, private router: Router) {
    this.filteredServices = this.fruitCtrl.valueChanges.pipe(
      startWith(null),
      map((fruit: string | null) => ( typeof fruit === 'string' ? this._filter(fruit) : this.allServices?.slice())),
    );
  }
  
  ngOnInit(): void {
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
  
  onSubmitRegister() {
    var registerRequestDto: EmployeeRegisterDto = new EmployeeRegisterDto();

    if (this.registerForm.controls.email.value)
      registerRequestDto.email = this.registerForm.controls.email.value;

    if (this.registerForm.controls.name.value)
      registerRequestDto.name = this.registerForm.controls.name.value;

    if (this.registerForm.controls.surname.value)
      registerRequestDto.surname = this.registerForm.controls.surname.value;

    if (this.registerForm.controls.phone.value)
      registerRequestDto.phone = this.registerForm.controls.phone.value;

    registerRequestDto.role = "EMPLOYEE";

    if(this.services.length > 0){
      registerRequestDto.services = this.services.map(service => service.id);
    }

    this.employeeService.addEmployee(registerRequestDto).subscribe(response => {
      this.router.navigateByUrl('/employees');
    });
  }
}
