import { NgModule } from '@angular/core';
import { AsyncPipe, CommonModule, JsonPipe, NgIf } from '@angular/common';
import { NgxMatFileInputModule } from '@angular-material-components/file-input';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatChipsModule } from '@angular/material/chips';
import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatDividerModule } from '@angular/material/divider';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import { MatStepperModule } from '@angular/material/stepper';
import { MatTableModule } from '@angular/material/table';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { JwtModule } from '@auth0/angular-jwt';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { AppRoutingModule } from '../app-routing.module';
import { AppComponent } from '../app.component';
import { AdminAppointmentListComponent } from '../components/appointment/admin-appointment-list/admin-appointment-list.component';
import { ClientAppointmentListComponent } from '../components/appointment/client-appointment-list/client-appointment-list.component';
import { CreateAppointmentComponent } from '../components/appointment/create-appointment/create-appointment.component';
import { EmployeeAppointmentListComponent } from '../components/appointment/employee-appointment-list/employee-appointment-list.component';
import { EmployeeAddComponent } from '../components/employee/employee-add/employee-add.component';
import { EmployeeEditComponent } from '../components/employee/employee-edit/employee-edit.component';
import { EmployeeListComponent } from '../components/employee/employee-list/employee-list.component';
import { LandingComponent } from '../components/landing/landing.component';
import { LoginComponent } from '../components/login/login.component';
import { MyProfileComponent } from '../components/my-profile/my-profile.component';
import { NavigationComponent } from '../components/navigation/navigation.component';
import { RegisterComponent } from '../components/register/register.component';
import { AdminServiceAddComponent } from '../components/service/admin-service-add/admin-service-add.component';
import { AdminServiceEditComponent } from '../components/service/admin-service-edit/admin-service-edit.component';
import { AdminServiceListComponent } from '../components/service/admin-service-list/admin-service-list.component';
import { ServiceDetailsComponent } from '../components/service/service-details/service-details.component';
import { ServiceListComponent } from '../components/service/service-list/service-list.component';
import { ThankYouPageComponent } from '../components/thank-you-page/thank-you-page.component';
import { CalendarHeaderComponent } from '../utils/calendar-header.component';
import { FooterComponent } from './footer/footer.component';
import { NavbarComponent } from './navbar/navbar.component';
import { AppointmentModule } from '../components/appointment/appointment.module';

const modules = [ 
  BrowserModule,
  AppRoutingModule,
  BrowserAnimationsModule,
  HttpClientModule,
  NgbModule,
  MatFormFieldModule,
  MatInputModule,
  MatDatepickerModule,
  MatNativeDateModule,
  MatListModule,
  MatDividerModule,
  MatSelectModule,
  FormsModule,
  ReactiveFormsModule,
  MatChipsModule,
  FormsModule, 
  JsonPipe,
  NgIf,
  MatButtonModule,
  MatExpansionModule,
  MatStepperModule,
  MatRadioModule,
  MatCardModule,
  MatIconModule,
  MatTableModule,
  MatCheckboxModule,
  AsyncPipe,
  MatAutocompleteModule,
  NgxMatFileInputModule,
  // JwtModule.forRoot({
  //   config: {
  //     tokenGetter: () => {
  //       return localStorage.getItem("token");
  //     },
  //     allowedDomains: ["localhost:8080"],
  //     disallowedRoutes: ["http://localhost:8080/api/v1/auth"],
  //   },
  // }),
  // CalendarModule.forRoot({ provide: DateAdapter, useFactory: adapterFactory }),

];

const components = [
  AppComponent,
  NavbarComponent,
  FooterComponent,
  LandingComponent,
  ServiceListComponent,
  ServiceDetailsComponent,
  NavigationComponent,
  ThankYouPageComponent,
  LoginComponent,
  RegisterComponent,
  MyProfileComponent,
  AdminServiceAddComponent,
  AdminServiceEditComponent,
  AdminServiceListComponent,
  ];

@NgModule({
  declarations: [...components],
  imports: [...modules],
  exports: [ ...modules,  ...components],
})
export class SharedModule { }
