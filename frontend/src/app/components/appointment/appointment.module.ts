import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from 'src/app/shared/shared.module';
import { AdminAppointmentListComponent } from './admin-appointment-list/admin-appointment-list.component';
import { ClientAppointmentListComponent } from './client-appointment-list/client-appointment-list.component';
import { CreateAppointmentComponent } from './create-appointment/create-appointment.component';
import { EmployeeAppointmentListComponent } from './employee-appointment-list/employee-appointment-list.component';
import { CalendarHeaderComponent } from 'src/app/shared/calendar-header/calendar-header.component';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { AppointmentEditComponent } from './appointment-edit/appointment-edit.component';
import { AppointmentRescheduleComponent } from './appointment-reschedule/appointment-reschedule.component';
import { AppointmentDetailsDialogComponent } from './admin-appointment-list/appointment-details-dialog/appointment-details-dialog.component';

@NgModule({
  declarations: [
    ClientAppointmentListComponent,
    EmployeeAppointmentListComponent,
    AdminAppointmentListComponent,
    CreateAppointmentComponent,
    CalendarHeaderComponent,
    AppointmentEditComponent,
    AppointmentRescheduleComponent,
    AppointmentDetailsDialogComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    CalendarModule.forRoot({ provide: DateAdapter, useFactory: adapterFactory }),
  ],
  exports:[
    ClientAppointmentListComponent,
    EmployeeAppointmentListComponent,
    AdminAppointmentListComponent,
    CreateAppointmentComponent,]
})
export class AppointmentModule { }
