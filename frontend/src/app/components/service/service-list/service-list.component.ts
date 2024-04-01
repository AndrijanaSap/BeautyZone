import { Component, OnInit } from '@angular/core';
import { CategoryWithServicesDto } from 'src/app/models/category-with-services.model';
import { CategoryService } from 'src/app/services/category.service';
import { ServiceService } from 'src/app/services/service.service';

@Component({
  selector: 'app-service-list',
  templateUrl: './service-list.component.html',
  styleUrls: ['./service-list.component.css']
})
export class ServiceListComponent  implements OnInit {
  categories: CategoryWithServicesDto[];

  constructor(private categoryService: CategoryService, private serviceService: ServiceService) {}

  ngOnInit(): void {
    this.categoryService.getAllCategoriesWithServices().subscribe(data => {
      this.categories = data;
    });
  }

  delete(id: any) {
    this.serviceService.delete(id).subscribe(data => {
      if (data){
        this.ngOnInit();
      }
      else
        console.log("error while deleting service");
    });
  }
}
