import { LiveAnnouncer } from '@angular/cdk/a11y';
import { ENTER, COMMA } from '@angular/cdk/keycodes';
import { Component, ElementRef, ViewChild, inject } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { MatChipInputEvent } from '@angular/material/chips';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, startWith, map } from 'rxjs';
import { CategoryDto } from 'src/app/models/category.model';
import { EmployeeRegisterDto } from 'src/app/models/employee-register-dto.model';
import { EmployeeUpdateDto } from 'src/app/models/employee-update-dto.model';
import { EmployeeDto } from 'src/app/models/employee.model';
import { ServiceDto } from 'src/app/models/service.model';
import { UpdateServiceWithEmployeesDto } from 'src/app/models/update-service-with-employees.model';
import { UserDto } from 'src/app/models/user.model';
import { CategoryService } from 'src/app/services/category.service';
import { EmployeeService } from 'src/app/services/employee.service';
import { ServiceService } from 'src/app/services/service.service';

@Component({
  selector: 'app-admin-service-edit',
  templateUrl: './admin-service-edit.component.html',
  styleUrls: ['./admin-service-edit.component.css']
})
export class AdminServiceEditComponent {
  hide = true;
  updateForm = new FormGroup({
    id: new FormControl(0, [
      Validators.required
    ]),
    durationInMinutes: new FormControl(0, [
      Validators.required
    ]),
    name: new FormControl("", [
      Validators.required
    ]),
    price: new FormControl(0, [
      Validators.required
    ]),
    imgPath: new FormControl(""),
    description: new FormControl("", [
      Validators.required
    ]),
    category: new FormControl(0, [
      Validators.required
    ]),
  });
  separatorKeysCodes: number[] = [ENTER, COMMA];
  fruitCtrl = new FormControl('');
  filteredEmployees: Observable<EmployeeDto[]>;
  employees: EmployeeDto[] = [];
  allEmployees: EmployeeDto[] = [];
  imgPath: string | ArrayBuffer | null;
  selectedFile: any;
  allCategories: CategoryDto[] = [];

  @ViewChild('fruitInput') fruitInput: ElementRef<HTMLInputElement>;

  announcer = inject(LiveAnnouncer);
  constructor(private employeeService: EmployeeService,
    private serviceService: ServiceService,
    private categoryService: CategoryService,
    private router: Router, private route: ActivatedRoute) {
    this.filteredEmployees = this.fruitCtrl.valueChanges.pipe(
      startWith(null),
      map((fruit: string | null) => (typeof fruit === 'string' ? this._filter(fruit) : this.allEmployees?.slice())),
    );
    this.updateForm.controls.imgPath.valueChanges.subscribe((file: any) => {
      this.selectedFile = file;
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = (_event) => {
        this.imgPath = reader.result;
      }
    })
  }

  ngOnInit(): void {
    const serviceId = this.route.snapshot.paramMap.get('id');
    if (serviceId) {
      this.serviceService.getServiceWithEmployeesById(serviceId).subscribe(data => {
        if (data.id) this.updateForm.controls.id.setValue(data.id);
        if (data.name) this.updateForm.controls.name.setValue(data.name);
        if (data.price) this.updateForm.controls.price.setValue(data.price);
        if (data.durationInMinutes) this.updateForm.controls.durationInMinutes.setValue(data.durationInMinutes);
        if (data.imgPath) this.imgPath = data.imgPath;
        if (data.description) this.updateForm.controls.description.setValue(data.description);
        if (data.category) this.updateForm.controls.category.setValue(data.category.id);
        if (data.employees) this.employees = data.employees;
      });
    }
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
    var updateServiceDto: UpdateServiceWithEmployeesDto = new UpdateServiceWithEmployeesDto();

    if (this.updateForm.controls.id.value)
      updateServiceDto.id = this.updateForm.controls.id.value;

    if (this.updateForm.controls.price.value)
      updateServiceDto.price = this.updateForm.controls.price.value;

    if (this.updateForm.controls.name.value)
      updateServiceDto.name = this.updateForm.controls.name.value;

    if (this.updateForm.controls.durationInMinutes.value)
      updateServiceDto.durationInMinutes = this.updateForm.controls.durationInMinutes.value;

    if (this.updateForm.controls.description.value)
      updateServiceDto.description = this.updateForm.controls.description.value;

    if (this.updateForm.controls.category.value)
      updateServiceDto.categoryId = this.updateForm.controls.category.value;

    if (this.employees.length > 0) {
      updateServiceDto.employees = this.employees.map(service => service.id);
    }

    const formData = new FormData();

    if (this.selectedFile)
      formData.append('img', this.selectedFile, this.selectedFile.name);

    formData.append('updateDto', new Blob([JSON.stringify(updateServiceDto)], {
      type: 'application/json'
    }));

    this.serviceService.updateService(formData).subscribe(response => {
      this.router.navigateByUrl('/services/' + updateServiceDto.id);
    });
  }

}
