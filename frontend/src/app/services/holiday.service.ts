import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HolidayRequestDto } from '../models/holiday-request-dto.model';
import { HolidayWithEmployeeResponseDto } from '../models/holiday-with-employee-response-dto.model';

@Injectable({
  providedIn: 'root'
})
export class HolidayService {
  private apiUrl = 'http://localhost:8080/api/v1/holidays';
  
  constructor(private http: HttpClient) { }

  createHoliday(createHolidayRequestDto: HolidayRequestDto): Observable<HolidayWithEmployeeResponseDto> {
    return this.http.post<HolidayWithEmployeeResponseDto>(this.apiUrl + '/createHoliday', createHolidayRequestDto);
  }

  updateHoliday(updateHolidayRequestDto: HolidayRequestDto): Observable<Boolean> {
    return this.http.post<Boolean>(this.apiUrl + '/updateHoliday', updateHolidayRequestDto);
  }

  getHolidayById(id: string): Observable<HolidayWithEmployeeResponseDto> {
    return this.http.get<HolidayWithEmployeeResponseDto>(`${this.apiUrl}/${id}`);
  }

  getAllHolidays(): Observable<HolidayWithEmployeeResponseDto[]> {
    return this.http.get<HolidayWithEmployeeResponseDto[]>(`${this.apiUrl}/getAll`);
  }

  getAllHolidayByEmployeeId(id: string): Observable<HolidayWithEmployeeResponseDto[]> {
    return this.http.get<HolidayWithEmployeeResponseDto[]>(`${this.apiUrl}/getAllByEmployeeId/${id}`);
  }

  delete(id: string): Observable<boolean> {
    return this.http.delete<boolean>(this.apiUrl + '/delete/' + id);
  }
}
