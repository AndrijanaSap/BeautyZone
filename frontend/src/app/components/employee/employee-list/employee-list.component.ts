import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { EmployeeDto } from 'src/app/models/employee.model';
import { EmployeeService } from 'src/app/services/employee.service';

@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.css']
})
export class EmployeeListComponent {
  displayedColumns: string[] = ['id', 'name', 'surname', 'phone', 'email', 'services', 'actions'];
  dataSource: EmployeeDto[];

  constructor(private employeeService: EmployeeService, private router: Router) {
  }

  ngOnInit(): void {
    this.employeeService.getAllEmployees().subscribe(data => {
      this.dataSource = data;
    });
  }

  delete(id: any) {
    this.employeeService.delete(id).subscribe(data => {
      if (data){
        this.ngOnInit();
      }
      else
        console.log("error while deleting user");
    });
  }
}
