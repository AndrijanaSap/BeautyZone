import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppointmentRequestDto } from '../models/appointmentRequest.model';
import { AvailabilityResponseDto } from '../models/availabilityResponse.model';
import { LoginRequestDto } from '../models/login-request-dto.model';
import { RegisterRequestDto } from '../models/register-request-dto.model';
import { AuthenticationResponseDto } from '../models/authentication-response-dto.model';
import { ServiceDto } from '../models/service.model';
import { UserDto } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8080/api/v1/users';
  
  constructor(private http: HttpClient) {}

  getUserById(id: string): Observable<UserDto> {
    return this.http.get<UserDto>(`${this.apiUrl}/${id}`);
  }

  updateUser(user: UserDto): Observable<UserDto> {
    return this.http.post<UserDto>(`${this.apiUrl}/update`, user);
  }
}
