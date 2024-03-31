import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthenticationResponseDto } from '../models/authentication-response-dto.model';
import { LoginRequestDto } from '../models/login-request-dto.model';
import { RegisterRequestDto } from '../models/register-request-dto.model';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private apiUrl = 'http://localhost:8080/api/v1/auth';
  
  constructor(private http: HttpClient) {}

  login(loginRequestDto: LoginRequestDto): Observable<AuthenticationResponseDto> {
    return this.http.post<AuthenticationResponseDto>(this.apiUrl + '/authenticate', loginRequestDto);
  }

  register(registerRequestDto: RegisterRequestDto): Observable<AuthenticationResponseDto> {
    return this.http.post<AuthenticationResponseDto>(this.apiUrl + '/register', registerRequestDto);
  }
}
