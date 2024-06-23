import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ServiceWithEmployeesDto } from 'src/app/models/service-with-employees.model';
import { ServiceDto } from 'src/app/models/service.model';
import { ServiceService } from 'src/app/services/service.service';

@Component({
    selector: 'app-landing',
    templateUrl: './landing.component.html',
    styleUrls: ['./landing.component.scss']
})

export class LandingComponent implements OnInit {
  focus: any;
  focus1: any;
  popularServices: ServiceDto[];

  constructor(private serviceService: ServiceService, private router: Router) { }

  ngOnInit() {
    this.serviceService.getPopularServices().subscribe(data => {
      this.popularServices = data;
    });
  }

}
