import { Component, Directive, EventEmitter, Input, OnInit, Output, QueryList, ViewChildren } from '@angular/core';
import { CategoryWithServicesDto } from 'src/app/models/category-with-services.model';

export type SortColumn = keyof CategoryWithServicesDto | '';
export type SortDirection = 'asc' | 'desc' | '';

export const rotate: { [key: string]: SortDirection } = { asc: 'desc', desc: '', '': 'asc' };
export const compare = (v1: any, v2:any) => (v1 < v2 ? -1 : v1 > v2 ? 1 : 0);

export  interface SortEvent {
	column: SortColumn;
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
export class NgbdSortableHeaderCategory {
	@Input() sortable: SortColumn = '';
	@Input() direction: SortDirection = '';
	@Output() sort = new EventEmitter<SortEvent>();

	rotate() {
		this.direction = rotate[this.direction];
		this.sort.emit({ column: this.sortable, direction: this.direction });
	}
}
