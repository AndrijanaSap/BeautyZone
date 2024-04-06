import { Component, Directive, EventEmitter, Input, OnInit, Output, QueryList, ViewChildren } from '@angular/core';
import { EmployeeDto } from 'src/app/models/employee.model';

export type SortColumnEmployee = keyof EmployeeDto | '';
export type SortDirection = 'asc' | 'desc' | '';

const rotate: { [key: string]: SortDirection } = { asc: 'desc', desc: '', '': 'asc' };

export  interface SortEventEmployee {
	column: SortColumnEmployee;
	direction: SortDirection;
}
@Directive({
	selector: 'th[sortable]',
	standalone: true,
	host: {
		'[class.asc]': 'direction === "asc"',
		'[class.desc]': 'direction === "desc"',
		'(click)': 'rotate()',
	},
})
export class NgbdSortableHeaderEmployee {
	@Input() sortable: SortColumnEmployee = '';
	@Input() direction: SortDirection = '';
	@Output() sort = new EventEmitter<SortEventEmployee>();

	rotate() {
		this.direction = rotate[this.direction];
		this.sort.emit({ column: this.sortable, direction: this.direction });
	}
}
