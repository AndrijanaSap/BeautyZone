import { NgModule } from '@angular/core';
import { RouterLink, RouterModule, Routes } from '@angular/router';
import { CommonModule, } from '@angular/common';
import { BrowserModule  } from '@angular/platform-browser';
import { LandingComponent } from './components/landing/landing.component';
import { ServiceListComponent } from './components/service/service-list/service-list.component';
import { ServiceDetailsComponent } from './components/service/service-details/service-details.component';
import { ThankYouPageComponent } from './components/thank-you-page/thank-you-page.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { MyProfileComponent } from './components/my-profile/my-profile.component';
import { CreateAppointmentComponent } from './components/appointment/create-appointment/create-appointment.component';
import { ClientAppointmentListComponent } from './components/appointment/client-appointment-list/client-appointment-list.component';
import { EmployeeListComponent } from './components/employee/employee-list/employee-list.component';
import { EmployeeEditComponent } from './components/employee/employee-edit/employee-edit.component';
import { EmployeeAddComponent } from './components/employee/employee-add/employee-add.component';
import { AdminServiceListComponent } from './components/service/admin-service-list/admin-service-list.component';
import { AdminServiceAddComponent } from './components/service/admin-service-add/admin-service-add.component';
import { AdminServiceEditComponent } from './components/service/admin-service-edit/admin-service-edit.component';
import { EmployeeAppointmentListComponent } from './components/appointment/employee-appointment-list/employee-appointment-list.component';
import { CategoryAddComponent } from './components/category/category-add/category-add.component';
import { CategoryEditComponent } from './components/category/category-edit/category-edit.component';
import { CategoryListComponent } from './components/category/category-list/category-list.component';
import { ClientEditComponent } from './components/client/client-edit/client-edit.component';
import { ClientListComponent } from './components/client/client-list/client-list.component';
import { AdminAppointmentListComponent } from './components/appointment/admin-appointment-list/admin-appointment-list.component';
import { AppointmentEditComponent } from './components/appointment/appointment-edit/appointment-edit.component';
import { AppointmentRescheduleComponent } from './components/appointment/appointment-reschedule/appointment-reschedule.component';
import { HolidayAddComponent } from './components/holiday/holiday-add/holiday-add.component';
import { HolidayEditComponent } from './components/holiday/holiday-edit/holiday-edit.component';
import { HolidayListComponent } from './components/holiday/holiday-list/holiday-list.component';
import { ShiftAddComponent } from './components/shift/shift-add/shift-add.component';
import { ShiftEditComponent } from './components/shift/shift-edit/shift-edit.component';
import { ShiftListComponent } from './components/shift/shift-list/shift-list.component';


const routes: Routes =[
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: LandingComponent },
  { path: 'services', component: ServiceListComponent },
  { path: 'services/:id', component: ServiceDetailsComponent },
  { path: 'add-service', component: AdminServiceAddComponent },
  { path: 'edit-service/:id', component: AdminServiceEditComponent },
  { path: 'create-appointment', component: CreateAppointmentComponent },
  { path: 'thank-you/:appointmentId', component: ThankYouPageComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'client-appointments', component: ClientAppointmentListComponent },
  { path: 'my-profile', component: MyProfileComponent },
  { path: 'employees', component: EmployeeListComponent },
  { path: 'add-employee', component: EmployeeAddComponent },
  { path: 'edit-employee/:id', component: EmployeeEditComponent },
  { path: 'clients', component: ClientListComponent },
  { path: 'edit-client/:id', component: ClientEditComponent },
  { path: 'admin-services', component: AdminServiceListComponent },
  { path: 'employee-appointments', component: EmployeeAppointmentListComponent},
  { path: 'add-category', component: CategoryAddComponent },
  { path: 'edit-category/:id', component: CategoryEditComponent },
  { path: 'categories', component: CategoryListComponent },
  { path: 'appointments', component: AdminAppointmentListComponent },
  { path: 'edit-appointment/:id', component: AppointmentEditComponent },
  { path: 'add-holiday', component: HolidayAddComponent },
  { path: 'edit-holiday/:id', component: HolidayEditComponent },
  { path: 'holidays', component: HolidayListComponent },
  { path: 'add-shift', component: ShiftAddComponent },
  { path: 'edit-shift/:id', component: ShiftEditComponent },
  { path: 'shifts', component: ShiftListComponent },
];


@NgModule({
  imports: [
    CommonModule,
    BrowserModule,
    RouterModule.forRoot(routes),
    RouterLink
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
