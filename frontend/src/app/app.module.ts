import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatNativeDateModule } from '@angular/material/core';
import { MatDividerModule } from '@angular/material/divider';
import { MatListModule } from '@angular/material/list';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatSelectModule } from '@angular/material/select';
import {MatChipsModule} from '@angular/material/chips';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {NativeDateAdapter} from '@angular/material/core';

import { NavbarComponent } from './shared/navbar/navbar.component';
import { FooterComponent } from './shared/footer/footer.component';
import { LandingComponent } from './components/landing/landing.component';
import { ServiceListComponent } from './components/service/service-list/service-list.component';
import { ServiceDetailsComponent } from './components/service/service-details/service-details.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NavigationComponent } from './components/navigation/navigation.component';
import { CategoryService } from './services/category.service';
import { ServiceService } from './services/service.service';
import { HttpClientModule } from '@angular/common/http';
import { EmployeeService } from './services/employee.service';
import { AppointmentService } from './services/appointment.service';
import {NgIf, JsonPipe, AsyncPipe} from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import {MatAccordion, MatExpansionModule} from '@angular/material/expansion';
import {MatStepperModule} from '@angular/material/stepper';
import {MatRadioModule} from '@angular/material/radio';
import {MatCardModule} from '@angular/material/card';
import { ThankYouPageComponent } from './components/thank-you-page/thank-you-page.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { MatIconModule } from '@angular/material/icon';
import { JwtModule } from '@auth0/angular-jwt';
import { MyProfileComponent } from './components/my-profile/my-profile.component';
import { ClientAppointmentListComponent } from './components/appointment/client-appointment-list/client-appointment-list.component';
import { EmployeeAppointmentListComponent } from './components/appointment/employee-appointment-list/employee-appointment-list.component';
import { AdminAppointmentListComponent } from './components/appointment/admin-appointment-list/admin-appointment-list.component';
import { CreateAppointmentComponent } from './components/appointment/create-appointment/create-appointment.component';
import {MatTableModule} from '@angular/material/table';
import { EmployeeListComponent } from './components/employee/employee-list/employee-list.component';
import { EmployeeEditComponent } from './components/employee/employee-edit/employee-edit.component';
import { EmployeeAddComponent } from './components/employee/employee-add/employee-add.component';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { AdminServiceAddComponent } from './components/service/admin-service-add/admin-service-add.component';
import { AdminServiceEditComponent } from './components/service/admin-service-edit/admin-service-edit.component';
import { AdminServiceListComponent } from './components/service/admin-service-list/admin-service-list.component';

@NgModule({
  declarations: [
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
    ClientAppointmentListComponent,
    EmployeeAppointmentListComponent,
    AdminAppointmentListComponent,
    CreateAppointmentComponent,
    EmployeeListComponent,
    EmployeeEditComponent,
    EmployeeAddComponent,
    AdminServiceAddComponent,
    AdminServiceEditComponent,
    AdminServiceListComponent
  ],
  imports: [
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
    JwtModule.forRoot({
      config: {
        tokenGetter: () => {
          return localStorage.getItem("token");
        },
        allowedDomains: ["localhost:8080"],
        disallowedRoutes: ["http://localhost:8080/api/v1/auth"],
      },
    }),
  ],
  
  providers: [CategoryService, ServiceService, EmployeeService, AppointmentService],
  bootstrap: [AppComponent]
})
export class AppModule { }
