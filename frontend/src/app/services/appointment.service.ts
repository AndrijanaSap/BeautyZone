import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CreateAppointmentRequestDto } from '../models/createAppointmentRequest.model';
import { CreateAppointmentResponseDto } from '../models/createAppointmentResponse.model';
import { AppointmentWithEmployeeResponseDto } from '../models/appointment-with-employee-response-dto.model';
import { AppointmentDto } from '../models/appointment.model';
import { AppointmentWithClientResponseDto } from '../models/appointment-with-client-response-dto.model';


interface Holiday {
  date: string;
  localName: string;
}

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {
  private countryCode = 'mk';

  private apiUrl = 'http://localhost:8080/api/v1/appointments';
  private holidayUrl = 'https://date.nager.at/api/v1/get/'+ this.countryCode +'/2024';

  constructor(private http: HttpClient) { }

  createAppointment(createAppointmentRequestDto: CreateAppointmentRequestDto): Observable<AppointmentWithEmployeeResponseDto> {
    return this.http.post<AppointmentWithEmployeeResponseDto>(this.apiUrl + '/createAppointment', createAppointmentRequestDto);
  }

  async calculateSignature(paramsMap: Map<string, string>): Promise<string | undefined> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json'); // Set Content-Type header
    return await this.http.post<string>(this.apiUrl + '/calculateSignature',
      JSON.stringify(Object.fromEntries(paramsMap)), { headers: headers }).toPromise();
  }

  getAppointmentById(id: string): Observable<AppointmentWithEmployeeResponseDto> {
    return this.http.get<AppointmentWithEmployeeResponseDto>(`${this.apiUrl}/${id}`);
  }

  getAllAppointmentByClientId(id: string): Observable<AppointmentWithEmployeeResponseDto[]> {
    return this.http.get<AppointmentWithEmployeeResponseDto[]>(`${this.apiUrl}/getAllByClientId/${id}`);
  }

  getAllAppointmentByEmployeeId(id: string): Observable<AppointmentWithClientResponseDto[]> {
    return this.http.get<AppointmentWithClientResponseDto[]>(`${this.apiUrl}/getAllByEmployeeId/${id}`);
  }

  getHolidays(): Observable<Holiday[]> {
    return this.http.get<Holiday[] >(this.holidayUrl);
  }
}
