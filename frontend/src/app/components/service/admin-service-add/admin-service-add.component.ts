import { LiveAnnouncer } from '@angular/cdk/a11y';
import { Component, ElementRef, ViewChild, inject } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { MatChipInputEvent } from '@angular/material/chips';
import { Router } from '@angular/router';
import { Observable, startWith, map } from 'rxjs';
import { EmployeeRegisterDto } from 'src/app/models/employee-register-dto.model';
import { EmployeeService } from 'src/app/services/employee.service';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { MatAutocompleteSelectedEvent, MatAutocompleteModule } from '@angular/material/autocomplete';
import { ServiceService } from 'src/app/services/service.service';
import { ServiceDto } from 'src/app/models/service.model';
import { EmployeeDto } from 'src/app/models/employee.model';
import { UpdateServiceWithEmployeesDto } from 'src/app/models/update-service-with-employees.model';
import { CategoryService } from 'src/app/services/category.service';
import { CategoryDto } from 'src/app/models/category.model';

@Component({
  selector: 'app-admin-service-add',
  templateUrl: './admin-service-add.component.html',
  styleUrls: ['./admin-service-add.component.css']
})
export class AdminServiceAddComponent {
  hide = true;
  addForm = new FormGroup({
    durationInMinutes: new FormControl(0, [
      Validators.required
    ]),
    name: new FormControl("", [
      Validators.required
    ]),
    price: new FormControl(0, [
      Validators.required
    ]),
    imgPath: new FormControl("", [
      Validators.required
    ]),
    description: new FormControl("", [
      Validators.required
    ]),
    category: new FormControl(0),
  });
  separatorKeysCodes: number[] = [ENTER, COMMA];
  fruitCtrl = new FormControl('');
  filteredEmployees: Observable<EmployeeDto[]>;
  employees: EmployeeDto[] = [];
  allEmployees: EmployeeDto[] = [];
  allCategories: CategoryDto[] = [];
  imgPath: string | ArrayBuffer | null;
  selectedFile: any;

  @ViewChild('fruitInput') fruitInput: ElementRef<HTMLInputElement>;

  announcer = inject(LiveAnnouncer);

  constructor(private employeeService: EmployeeService, private categoryService: CategoryService, private serviceService: ServiceService, private router: Router) {
    this.filteredEmployees = this.fruitCtrl.valueChanges.pipe(
      startWith(null),
      map((fruit: string | null) => (typeof fruit === 'string' ? this._filter(fruit) : this.allEmployees?.slice())),
    );
    this.addForm.controls.imgPath.valueChanges.subscribe((file: any) => {
      this.selectedFile = file;
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = (_event) => {
        this.imgPath = reader.result;
      }
    })
  }

  ngOnInit(): void {
    this.employeeService.getAllEmployees().subscribe(data => {
      this.allEmployees = data;
    });
    this.categoryService.getAllCategories().subscribe(data => {
      this.allCategories = data;
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
    var serviceDto: UpdateServiceWithEmployeesDto = new UpdateServiceWithEmployeesDto();

    if (this.addForm.controls.price.value)
      serviceDto.price = this.addForm.controls.price.value;

    if (this.addForm.controls.name.value)
      serviceDto.name = this.addForm.controls.name.value;

    if (this.addForm.controls.durationInMinutes.value)
      serviceDto.durationInMinutes = this.addForm.controls.durationInMinutes.value;

    if (this.addForm.controls.description.value)
      serviceDto.description = this.addForm.controls.description.value;

    if (this.addForm.controls.category.value)
      serviceDto.categoryId = this.addForm.controls.category.value;

    if (this.employees.length > 0) {
      serviceDto.employees = this.employees.map(service => service.id);
    }

    const formData = new FormData();

    if (this.selectedFile)
      formData.append('img', this.selectedFile, this.selectedFile.name);

    formData.append('addDto', new Blob([JSON.stringify(serviceDto)], {
      type: 'application/json'
    }));
    
    this.serviceService.addService(formData).subscribe(response => {
      this.router.navigateByUrl('/services/' + response);
    });
  }
}
