import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EmployeeDto } from '../models/employee.model';
import { EmployeeRegisterDto } from '../models/employee-register-dto.model';
import { EmployeeUpdateDto } from '../models/employee-update-dto.model';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  private apiUrl = 'http://localhost:8080/api/v1/employees';
  
  constructor(private http: HttpClient) {}

  getEmployeesByServiceId(id: number): Observable<EmployeeDto[]> {
    return this.http.get<EmployeeDto[]>(`${this.apiUrl}/${id}`);
  }

  getAllEmployees(): Observable<EmployeeDto[]> {
    return this.http.get<EmployeeDto[]>(`${this.apiUrl}`);
  }

  getEmployeeById(id: string): Observable<EmployeeDto> {
    return this.http.get<EmployeeDto>(this.apiUrl + '/get/' + id);
  }

  delete(id: string): Observable<boolean> {
    return this.http.delete<boolean>(this.apiUrl + '/delete/' + id);
  }

  addEmployee(registerRequestDto: EmployeeRegisterDto): Observable<void> {
    return this.http.post<void>(this.apiUrl + '/add', registerRequestDto);
  }

  updateEmployee(employeeUpdateDto: EmployeeUpdateDto) {
    return this.http.put(this.apiUrl + '/update', employeeUpdateDto);
}
}
