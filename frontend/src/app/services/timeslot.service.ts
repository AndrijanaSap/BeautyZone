import { Injectable } from '@angular/core';
import { AvailabilityResponseDto } from '../models/availabilityResponse.model';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AppointmentRequestDto } from '../models/appointmentRequest.model';

@Injectable({
  providedIn: 'root'
})
export class TimeslotService {
  private apiUrl = 'http://localhost:8080/api/v1/timeslots';
  
  constructor(private http: HttpClient) {}

  checkAvailability(appointmentRequestDto: AppointmentRequestDto): Observable<AvailabilityResponseDto[]> {
    return this.http.post<AvailabilityResponseDto[]>(this.apiUrl + '/checkAvailability', appointmentRequestDto);
  }

}
