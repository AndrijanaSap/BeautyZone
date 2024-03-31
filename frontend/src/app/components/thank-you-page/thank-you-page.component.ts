import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AppointmentResponseDto } from 'src/app/models/appointment-response-dto.model';
import { AppointmentDto } from 'src/app/models/appointment.model';
import { AppointmentService } from 'src/app/services/appointment.service';

@Component({
  selector: 'app-thank-you-page',
  templateUrl: './thank-you-page.component.html',
  styleUrls: ['./thank-you-page.component.css']
})
export class ThankYouPageComponent implements OnInit {
  appointment: AppointmentResponseDto;
  constructor(public route: ActivatedRoute, private appointmentService: AppointmentService,) {}
  
  ngOnInit(): void {
    const appointmentId = this.route.snapshot.params['appointmentId'];
    if (appointmentId){
      this.appointmentService.getAppointmentById(appointmentId).subscribe(data => {
        this.appointment = data;
      });}

  }

}
