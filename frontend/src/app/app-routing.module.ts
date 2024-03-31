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


const routes: Routes =[
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: LandingComponent },
  { path: 'services', component: ServiceListComponent },
  { path: 'services/:id', component: ServiceDetailsComponent },
  { path: 'appointments', component: CreateAppointmentComponent },
  { path: 'thank-you/:appointmentId', component: ThankYouPageComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'client-appointments', component: ClientAppointmentListComponent },
  { path: 'my-profile', component: MyProfileComponent },
  { path: 'employees', component: EmployeeListComponent },
  { path: 'add-employee', component: EmployeeAddComponent },
  { path: 'edit-employee/:id', component: EmployeeEditComponent },
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
