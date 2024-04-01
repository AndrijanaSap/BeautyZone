import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { EmployeeDto } from 'src/app/models/employee.model';
import { ServiceWithEmployeesDto } from 'src/app/models/service-with-employees.model';
import { ServiceDto } from 'src/app/models/service.model';
import { EmployeeService } from 'src/app/services/employee.service';
import { ServiceService } from 'src/app/services/service.service';

@Component({
  selector: 'app-admin-service-list',
  templateUrl: './admin-service-list.component.html',
  styleUrls: ['./admin-service-list.component.css']
})
export class AdminServiceListComponent {
  displayedColumns: string[] = ['id', 'name', 'price', 'durationInMinutes', 'imgPath', 'category', 'employees', 'actions'];
  dataSource: ServiceWithEmployeesDto[];

  constructor(private serviceService: ServiceService, private router: Router) {
  }

  ngOnInit(): void {
    this.serviceService.getAllServicesWithEmployees().subscribe(data => {
      this.dataSource = data;
    });
  }

  delete(id: any) {
    this.serviceService.delete(id).subscribe(data => {
      if (data){
        this.ngOnInit();
      }
      else
        console.log("error while deleting service");
    });
  }
}
