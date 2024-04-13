import { Component, Directive, EventEmitter, Input, Output, QueryList, ViewChildren } from '@angular/core';
import { Router } from '@angular/router';
import { EmployeeDto } from 'src/app/models/employee.model';
import { EmployeeService } from 'src/app/services/employee.service';
import { NgbdSortableHeader, SortEvent } from '../../../shared/directives/ngbd-sortable-header.directive';

const compare = (v1: string, v2:string) => (v1 < v2 ? -1 : v1 > v2 ? 1 : 0);

@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.css']
})
export class EmployeeListComponent {
  @ViewChildren(NgbdSortableHeader) headers: QueryList<NgbdSortableHeader>;
  displayedColumns: string[] = ['id', 'name', 'surname', 'phone', 'email', 'services', 'actions'];
  dataSource: EmployeeDto[];
  initialDataSource: EmployeeDto[];

  constructor(private employeeService: EmployeeService, private router: Router) {
  }

  ngOnInit(): void {
    this.employeeService.getAllEmployees().subscribe(data => {
      this.dataSource = data;
      this.initialDataSource = data;
    });
  }


	onSort({ column, direction }: SortEvent) {
		// resetting other headers
		this.headers.forEach((header) => {
			if (header.sortable !== column) {
				header.direction = '';
			}
		});

		// sorting employees
		if (direction === '' || column === '') {
			this.dataSource = this.initialDataSource;
		} else {
			this.dataSource = [...this.initialDataSource].sort((a, b) => {
				const res = compare(a[column], b[column]);
				return direction === 'asc' ? res : -res;
			});
		}
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
