import { Component, OnInit } from '@angular/core';
import { ServiceDto } from 'src/app/models/service.model';
import { ServiceService } from 'src/app/services/service.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-service-details',
  templateUrl: './service-details.component.html',
  styleUrls: ['./service-details.component.css']
})
export class ServiceDetailsComponent implements OnInit{

  service: ServiceDto;

  constructor(private serviceService: ServiceService, private route: ActivatedRoute, private router: Router  ) {}

  ngOnInit(): void {
    const serviceId = this.route.snapshot.paramMap.get('id');
    if (serviceId){
    this.serviceService.getServiceById(serviceId).subscribe(data => {
      this.service = data;
    });}
  }
}
