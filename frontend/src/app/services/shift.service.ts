import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ShiftRequestDto } from '../models/shift-request-dto.model';
import { ShiftWithEmployeeResponseDto } from '../models/shift-with-employee-response-dto.model';
import { ShiftWithHolidayResponseDto } from '../models/shift-with-holiday-response-dto.model';

@Injectable({
  providedIn: 'root'
})
export class ShiftService{
  private apiUrl = 'http://localhost:8080/api/v1/shifts';
  
  constructor(private http: HttpClient) { }

  createShift(createShiftRequestDto: ShiftRequestDto): Observable<ShiftWithEmployeeResponseDto> {
    return this.http.post<ShiftWithEmployeeResponseDto>(`${this.apiUrl}/createShift`, createShiftRequestDto);
  }

  updateShift(updateShiftRequestDto: ShiftRequestDto): Observable<Boolean> {
    return this.http.post<Boolean>(`${this.apiUrl}/updateShift`, updateShiftRequestDto);
  }

  getShiftById(id: string): Observable<ShiftWithEmployeeResponseDto> {
    return this.http.get<ShiftWithEmployeeResponseDto>(`${this.apiUrl}/${id}`);
  }

  getAllShifts(): Observable<ShiftWithEmployeeResponseDto[]> {
    return this.http.get<ShiftWithEmployeeResponseDto[]>(`${this.apiUrl}/getAll`);
  }

  getAllShiftByEmployeeId(id: string): Observable<ShiftWithEmployeeResponseDto[]> {
    return this.http.get<ShiftWithEmployeeResponseDto[]>(`${this.apiUrl}/getAllByEmployeeId/${id}`);
  }

  getAllByEmployeeIdWithHolidays(id: string): Observable<ShiftWithHolidayResponseDto[]> {
    return this.http.get<ShiftWithHolidayResponseDto[]>(`${this.apiUrl}/getAllByEmployeeIdWithHolidays/${id}`);
  }

  delete(id: string): Observable<boolean> {
    return this.http.delete<boolean>(`${this.apiUrl}/delete/${id}`);
  }
}
