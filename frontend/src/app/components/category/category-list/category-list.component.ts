import { Component, Directive, EventEmitter, Input, OnInit, Output, QueryList, ViewChildren } from '@angular/core';
import { Router } from '@angular/router';
import { CategoryWithServicesDto } from 'src/app/models/category-with-services.model';
import { CategoryService } from 'src/app/services/category.service';
import { NgbdSortableHeader, SortEvent } from 'src/app/shared/directives/ngbd-sortable-header.directive';

const compare = (v1: string, v2:string) => (v1 < v2 ? -1 : v1 > v2 ? 1 : 0);


@Component({
  selector: 'app-category-list',
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.css']
})
export class CategoryListComponent implements OnInit {
  @ViewChildren(NgbdSortableHeader) headers: QueryList<NgbdSortableHeader>;
  displayedColumns: string[] = ['id', 'name', 'jobPosition', 'services', 'actions'];
  dataSource: CategoryWithServicesDto[];
  initialDataSource: CategoryWithServicesDto[];

  constructor(private categoryService: CategoryService, private router: Router) {
  }

  ngOnInit(): void {
    this.categoryService.getAllCategoriesWithServices().subscribe(data => {
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

		// sorting categories
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
    this.categoryService.delete(id).subscribe(data => {
      if (data){
        this.ngOnInit();
      }
      else
        console.log("error while deleting user");
    });
  }

}
