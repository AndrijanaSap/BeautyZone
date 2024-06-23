import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from 'src/app/shared/shared.module';
import { ShiftAddComponent } from './shift-add/shift-add.component';
import { ShiftEditComponent } from './shift-edit/shift-edit.component';
import { ShiftListComponent } from './shift-list/shift-list.component';

@NgModule({
  declarations: [
    ShiftAddComponent,
    ShiftEditComponent,
    ShiftListComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
  ],
  exports:[
  ]
})
export class ShiftModule { }
