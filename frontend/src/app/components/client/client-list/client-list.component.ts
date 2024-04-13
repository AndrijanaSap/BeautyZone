import { Component, Directive, EventEmitter, Input, Output, QueryList, ViewChildren } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import { UserDto } from 'src/app/models/user.model';
import { NgbdSortableHeader, SortEvent } from 'src/app/shared/directives/ngbd-sortable-header.directive';

const compare = (v1: string, v2:string) => (v1 < v2 ? -1 : v1 > v2 ? 1 : 0);

@Component({
  selector: 'app-client-list',
  templateUrl: './client-list.component.html',
  styleUrls: ['./client-list.component.css']
})
export class ClientListComponent {
  @ViewChildren(NgbdSortableHeader) headers: QueryList<NgbdSortableHeader>;
  displayedColumns: string[] = ['id', 'name', 'surname', 'phone', 'email', 'ipAddress', 'actions'];
  dataSource: UserDto[];
  initialDataSource: UserDto[];

  constructor(private userService: UserService, private router: Router) {
  }

  ngOnInit(): void {
    this.userService.getAllClients().subscribe(data => {
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
    this.userService.delete(id).subscribe(data => {
      if (data){
        this.ngOnInit();
      }
      else
        console.log("error while deleting user");
    });
  }

}
