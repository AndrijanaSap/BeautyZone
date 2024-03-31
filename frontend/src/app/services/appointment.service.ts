import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CreateAppointmentRequestDto } from '../models/createAppointmentRequest.model';
import { CreateAppointmentResponseDto } from '../models/createAppointmentResponse.model';
import { AppointmentResponseDto } from '../models/appointment-response-dto.model';
import { AppointmentDto } from '../models/appointment.model';

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  private apiUrl = 'http://localhost:8080/api/v1/appointments';

  constructor(private http: HttpClient) { }

  createAppointment(createAppointmentRequestDto: CreateAppointmentRequestDto): Observable<AppointmentResponseDto> {
    return this.http.post<AppointmentResponseDto>(this.apiUrl + '/createAppointment', createAppointmentRequestDto);
  }

  async calculateSignature(paramsMap: Map<string, string>): Promise<string | undefined> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json'); // Set Content-Type header
    return await this.http.post<string>(this.apiUrl + '/calculateSignature',
      JSON.stringify(Object.fromEntries(paramsMap)), { headers: headers }).toPromise();
  }

  getAppointmentById(id: string): Observable<AppointmentResponseDto> {
    return this.http.get<AppointmentResponseDto>(`${this.apiUrl}/${id}`);
  }

  getAllAppointmentByClientId(id: string): Observable<AppointmentResponseDto[]> {
    return this.http.get<AppointmentResponseDto[]>(`${this.apiUrl}/getAllByClientId/${id}`);
  }
}
