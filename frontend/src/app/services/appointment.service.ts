import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CreateUpdateAppointmentRequestDto } from '../models/create-update-appointment-request-dto.model';
import { CreateAppointmentResponseDto } from '../models/createAppointmentResponse.model';
import { AppointmentWithEmployeeResponseDto } from '../models/appointment-with-employee-response-dto.model';
import { AppointmentDto } from '../models/appointment.model';
import { AppointmentWithClientResponseDto } from '../models/appointment-with-client-response-dto.model';
import { UpdateAppointmentCustomerData } from '../models/update-appointment-customer-data.model';


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
  private holidayUrl = 'https://date.nager.at/api/v1/get/' + this.countryCode + '/2024';

  constructor(private http: HttpClient) { }

  createAppointment(createAppointmentRequestDto: CreateUpdateAppointmentRequestDto): Observable<AppointmentWithEmployeeResponseDto> {
    return this.http.post<AppointmentWithEmployeeResponseDto>(this.apiUrl + '/createAppointment', createAppointmentRequestDto);
  }

  updateAppointment(updateAppointmentRequestDto: CreateUpdateAppointmentRequestDto): Observable<Boolean> {
    return this.http.post<Boolean>(this.apiUrl + '/updateAppointment', updateAppointmentRequestDto);
  }

  async calculateSignature(paramsMap: Map<string, string>): Promise<string | undefined> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json'); // Set Content-Type header
    return await this.http.post<string>(this.apiUrl + '/calculateSignature',
      JSON.stringify(Object.fromEntries(paramsMap)), { headers: headers }).toPromise();
  }

  getAppointmentById(id: string): Observable<AppointmentWithEmployeeResponseDto> {
    return this.http.get<AppointmentWithEmployeeResponseDto>(`${this.apiUrl}/${id}`);
  }

  getAllAppointments(): Observable<AppointmentWithEmployeeResponseDto[]> {
    return this.http.get<AppointmentWithEmployeeResponseDto[]>(`${this.apiUrl}/getAll`);
  }

  getAllAppointmentByClientId(id: string): Observable<AppointmentWithEmployeeResponseDto[]> {
    return this.http.get<AppointmentWithEmployeeResponseDto[]>(`${this.apiUrl}/getAllByClientId/${id}`);
  }

  getAllAppointmentByEmployeeId(id: string): Observable<AppointmentWithClientResponseDto[]> {
    return this.http.get<AppointmentWithClientResponseDto[]>(`${this.apiUrl}/getAllByEmployeeId/${id}`);
  }

  delete(id: string): Observable<boolean> {
    return this.http.delete<boolean>(this.apiUrl + '/delete/' + id);
  }

  updateAppointmentCustomerData(updateDto: UpdateAppointmentCustomerData): Observable<boolean> {
    return this.http.put<boolean>(`${this.apiUrl}/updateAppointmentCustomerData`, updateDto);
  }

  getHolidays(): Observable<Holiday[]> {
    return this.http.get<Holiday[]>(this.holidayUrl);
  }
}
