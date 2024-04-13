import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AppointmentWithEmployeeResponseDto } from 'src/app/models/appointment-with-employee-response-dto.model';

@Component({
  selector: 'app-appointment-details-dialog',
  templateUrl: './appointment-details-dialog.component.html',
  styleUrls: ['./appointment-details-dialog.component.css']
})
export class AppointmentDetailsDialogComponent {

  constructor(@Inject(MAT_DIALOG_DATA) public data: AppointmentWithEmployeeResponseDto) {
  }
}
