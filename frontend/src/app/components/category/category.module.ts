import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CategoryEditComponent } from './category-edit/category-edit.component';
import { NgbdSortableHeaderCategory } from './ngbd-sortable-header-category.directive';
import { CategoryAddComponent } from './category-add/category-add.component';
import { CategoryListComponent } from './category-list/category-list.component';
import { SharedModule } from 'src/app/shared/shared.module';



@NgModule({
  declarations: [
    CategoryEditComponent,
    CategoryAddComponent,
    CategoryEditComponent,
    CategoryListComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    NgbdSortableHeaderCategory
  ]
})
export class CategoryModule { }
