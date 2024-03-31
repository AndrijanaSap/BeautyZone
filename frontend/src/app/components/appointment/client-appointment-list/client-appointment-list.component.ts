import { Component } from '@angular/core';
import { AppointmentResponseDto } from 'src/app/models/appointment-response-dto.model';
import { AppointmentService } from 'src/app/services/appointment.service';


export interface ClientAppointmentDto {
  id: number;
  service: string;
  clientName: string;
  amount: number;
  dateTime: Date;
  paymentMethod: string;
  employee: string;
  note: string;
}

@Component({
  selector: 'app-client-appointment-list',
  templateUrl: './client-appointment-list.component.html',
  styleUrls: ['./client-appointment-list.component.css']
})
export class ClientAppointmentListComponent {

  displayedColumns: string[] = ['id', 'clientName', 'dateTime', 'service', 'amount', 'employee', 'paymentMethod', 'status', 'note'];
  dataSource :AppointmentResponseDto[];

  constructor(private appointmentService: AppointmentService) {
  }

  ngOnInit(): void {
    var id = localStorage.getItem('userId');
    if(id)
    this.appointmentService.getAllAppointmentByClientId(id).subscribe(data => {
      this.dataSource = data;
    });
  }
}
