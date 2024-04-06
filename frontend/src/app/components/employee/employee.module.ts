import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from 'src/app/shared/shared.module';
import { NgbdSortableHeaderEmployee } from './ngbd-sortable-header-employee.directive';
import { EmployeeAddComponent } from './employee-add/employee-add.component';
import { EmployeeEditComponent } from './employee-edit/employee-edit.component';
import { EmployeeListComponent } from './employee-list/employee-list.component';



@NgModule({
  declarations: [
    EmployeeListComponent,
    EmployeeEditComponent,
    EmployeeAddComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    NgbdSortableHeaderEmployee
  ]
})
export class EmployeeModule { }
