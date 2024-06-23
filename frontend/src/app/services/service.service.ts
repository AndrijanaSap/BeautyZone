import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ServiceDto } from '../models/service.model';
import { ServiceWithEmployeesDto } from '../models/service-with-employees.model';
import { UpdateServiceWithEmployeesDto } from '../models/update-service-with-employees.model';

@Injectable({
  providedIn: 'root'
})
export class ServiceService {
  private apiUrl = 'http://localhost:8080/api/v1/services';

  constructor(private http: HttpClient) {}

  getAllServices(): Observable<ServiceDto[]> {
    return this.http.get<ServiceDto[]>(this.apiUrl);
  }

  getAllServicesWithEmployees(): Observable<ServiceWithEmployeesDto[]> {
    return this.http.get<ServiceWithEmployeesDto[]>(this.apiUrl + "/with-employees");
  }

  getPopularServices(): Observable<ServiceDto[]> {
    return this.http.get<ServiceDto[]>(`${this.apiUrl}/get-popular`);
  }

  getServiceById(id: string): Observable<ServiceDto> {
    return this.http.get<ServiceDto>(`${this.apiUrl}/${id}`);
  }

  getServiceWithEmployeesById(id: string): Observable<ServiceWithEmployeesDto> {
    return this.http.get<ServiceWithEmployeesDto>(`${this.apiUrl}/with-employees/${id}`);
  }
  
  addService(addDto: FormData): Observable<string> {
    return this.http.post<string>(this.apiUrl + '/add', addDto);
  }

//   updateService(updateDto: UpdateServiceWithEmployeesDto) {
//     return this.http.put(this.apiUrl + '/update', updateDto);
// }

updateService(updateDto: FormData) {
  return this.http.put(this.apiUrl + '/update', updateDto);
}

  delete(id: string): Observable<boolean> {
    return this.http.delete<boolean>(this.apiUrl + '/delete/' + id);
  }
}
