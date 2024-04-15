import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from 'src/app/shared/shared.module';
import { HolidayAddComponent } from './holiday-add/holiday-add.component';
import { HolidayEditComponent } from './holiday-edit/holiday-edit.component';
import { HolidayListComponent } from './holiday-list/holiday-list.component';

@NgModule({
  declarations: [
    HolidayAddComponent,
    HolidayEditComponent,
    HolidayListComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
  ],
  exports:[
    HolidayAddComponent
  ]
})
export class HolidayModule { }
