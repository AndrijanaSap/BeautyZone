import { Component, OnInit } from '@angular/core';
import { CategoryDto } from 'src/app/models/category.model';
import { CategoryService } from 'src/app/services/category.service';

@Component({
  selector: 'app-service-list',
  templateUrl: './service-list.component.html',
  styleUrls: ['./service-list.component.css']
})
export class ServiceListComponent  implements OnInit {
  categoriesWithServices: CategoryDto[];

  constructor(private categoryService: CategoryService) {}

  ngOnInit(): void {
    this.categoryService.getAllCategories().subscribe(data => {
      this.categoriesWithServices = data;
    });
  }

}
