import { Component, Directive, EventEmitter, Input, OnInit, Output, QueryList, ViewChildren } from '@angular/core';
import { Router } from '@angular/router';
import { ShiftWithEmployeeResponseDto } from 'src/app/models/shift-with-employee-response-dto.model';
import { ShiftService } from 'src/app/services/shift.service';
import { NgbdSortableHeader, SortEvent } from 'src/app/shared/directives/ngbd-sortable-header.directive';

const compare = (v1: string, v2:string) => (v1 < v2 ? -1 : v1 > v2 ? 1 : 0);
@Component({
  selector: 'app-shift-list',
  templateUrl: './shift-list.component.html',
  styleUrls: ['./shift-list.component.css']
})
export class ShiftListComponent  implements OnInit {
  @ViewChildren(NgbdSortableHeader) headers: QueryList<NgbdSortableHeader>;
  displayedColumns: string[] = ['id', 'employee', 'name', 'shiftType', 'from', 'to', 'actions'];
  dataSource: ShiftWithEmployeeResponseDto[];
  initialDataSource: ShiftWithEmployeeResponseDto[];

  constructor(private shiftService: ShiftService, private router: Router) {
  }

  ngOnInit(): void {
    this.shiftService.getAllShifts().subscribe(data => {
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

		// sorting
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
    this.shiftService.delete(id).subscribe(data => {
      if (data){
        this.ngOnInit();
      }
      else
        console.log("error while deleting shift");
    });
  }

}
