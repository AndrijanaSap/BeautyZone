import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CategoryService } from './services/category.service';
import { ServiceService } from './services/service.service';
import { EmployeeService } from './services/employee.service';
import { AppointmentService } from './services/appointment.service';
import { JwtModule } from '@auth0/angular-jwt';
import { CategoryModule } from './components/category/category.module';
import { SharedModule } from './shared/shared.module';
import { EmployeeModule } from './components/employee/employee.module';
import { AppointmentModule } from './components/appointment/appointment.module';
import { ClientModule } from './components/client/client.module';

@NgModule({
  declarations: [
  ],
  imports: [
    AppRoutingModule,
    SharedModule,
    CategoryModule,
    EmployeeModule,
    AppointmentModule,
    ClientModule,
    JwtModule.forRoot({
      config: {
        tokenGetter: () => {
          return localStorage.getItem("token");
        },
        allowedDomains: ["localhost:8080"],
        disallowedRoutes: ["http://localhost:8080/api/v1/auth"],
      },
    }),
   
    // NgbdSortableHeaderCategory
    // NgbdSortableHeaderEmployee,
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
  ],

  providers: [CategoryService, ServiceService, EmployeeService, AppointmentService],
  bootstrap: [AppComponent]
})
export class AppModule { }
