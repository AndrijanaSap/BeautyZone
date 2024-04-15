import { Component, Directive, EventEmitter, Input, OnInit, Output, QueryList, ViewChildren } from '@angular/core';
import { Router } from '@angular/router';
import { HolidayWithEmployeeResponseDto } from 'src/app/models/holiday-with-employee-response-dto.model';
import { HolidayService } from 'src/app/services/holiday.service';
import { NgbdSortableHeader, SortEvent } from 'src/app/shared/directives/ngbd-sortable-header.directive';

const compare = (v1: string, v2:string) => (v1 < v2 ? -1 : v1 > v2 ? 1 : 0);
@Component({
  selector: 'app-holiday-list',
  templateUrl: './holiday-list.component.html',
  styleUrls: ['./holiday-list.component.css']
})
export class HolidayListComponent  implements OnInit {
  @ViewChildren(NgbdSortableHeader) headers: QueryList<NgbdSortableHeader>;
  displayedColumns: string[] = ['id', 'employee', 'name', 'holidayType', 'from', 'to', 'actions'];
  dataSource: HolidayWithEmployeeResponseDto[];
  initialDataSource: HolidayWithEmployeeResponseDto[];

  constructor(private holidayService: HolidayService, private router: Router) {
  }

  ngOnInit(): void {
    this.holidayService.getAllHolidays().subscribe(data => {
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
    this.holidayService.delete(id).subscribe(data => {
      if (data){
        this.ngOnInit();
      }
      else
        console.log("error while deleting holiday");
    });
  }

}
