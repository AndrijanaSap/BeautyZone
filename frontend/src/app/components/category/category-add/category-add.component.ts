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
import { ServiceDto } from 'src/app/models/service.model';
import { UpdateCategoryWithServicesDto } from 'src/app/models/update-category-with-services.model';
import { UpdateServiceWithEmployeesDto } from 'src/app/models/update-service-with-employees.model';
import { CategoryService } from 'src/app/services/category.service';
import { EmployeeService } from 'src/app/services/employee.service';
import { ServiceService } from 'src/app/services/service.service';

@Component({
  selector: 'app-category-add',
  templateUrl: './category-add.component.html',
  styleUrls: ['./category-add.component.css']
})
export class CategoryAddComponent  {
  hide = true;
  addForm = new FormGroup({
    id: new FormControl(0, [
      Validators.required
    ]),
    name: new FormControl("", [
      Validators.required
    ]),
    jobPosition: new FormControl("", [
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
  constructor(private serviceService: ServiceService,
    private categoryService: CategoryService,
    private router: Router, private route: ActivatedRoute) {
    this.filteredServices = this.fruitCtrl.valueChanges.pipe(
      startWith(null),
      map((fruit: string | null) => (typeof fruit === 'string' ? this._filter(fruit) : this.allServices?.slice())),
    );
  }

  ngOnInit(): void {
    const categoryId = this.route.snapshot.paramMap.get('id');
    if (categoryId) {
      this.categoryService.getCategoryWithServicesById(categoryId).subscribe(data => {
        if (data.id) this.addForm.controls.id.setValue(data.id);
        if (data.name) this.addForm.controls.name.setValue(data.name);
        if (data.jobPosition) this.addForm.controls.jobPosition.setValue(data.jobPosition);
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
    var updateCategoryDto: UpdateCategoryWithServicesDto = new UpdateCategoryWithServicesDto();

    if (this.addForm.controls.id.value)
      updateCategoryDto.id = this.addForm.controls.id.value;

    if (this.addForm.controls.jobPosition.value)
      updateCategoryDto.jobPosition = this.addForm.controls.jobPosition.value;

    if (this.addForm.controls.name.value)
      updateCategoryDto.name = this.addForm.controls.name.value;

    if (this.services.length > 0) {
      updateCategoryDto.services = this.services.map(service => service.id);
    }
  
    this.categoryService.addCategory(updateCategoryDto).subscribe(response => {
      this.router.navigateByUrl('/categories');
    });
  }

}


