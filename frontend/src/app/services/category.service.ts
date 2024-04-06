import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CategoryWithServicesDto } from '../models/category-with-services.model';
import { CategoryDto } from '../models/category.model';
import { UpdateCategoryWithServicesDto } from '../models/update-category-with-services.model';

@Injectable({
  providedIn: 'root'
})

export class CategoryService {
  private apiUrl = 'http://localhost:8080/api/v1/categories';
  
  constructor(private http: HttpClient) {}

  getAllCategoriesWithServices(): Observable<CategoryWithServicesDto[]> {
    return this.http.get<CategoryWithServicesDto[]>(this.apiUrl + "/with-services");
  }
  getAllCategories(): Observable<CategoryDto[]> {
    return this.http.get<CategoryDto[]>(this.apiUrl);
  }
  getCategoryWithServicesById(id: string): Observable<CategoryWithServicesDto> {
    return this.http.get<CategoryWithServicesDto>(`${this.apiUrl}/with-services/${id}`);
  }
  addCategory(category: UpdateCategoryWithServicesDto): Observable<number> {
    return this.http.post<number>(`${this.apiUrl}/add`, category);
  }
  updateCategory(category: UpdateCategoryWithServicesDto): Observable<CategoryWithServicesDto> {
    return this.http.put<CategoryWithServicesDto>(`${this.apiUrl}/update`, category);
  }
  delete(id: string): Observable<boolean> {
    return this.http.delete<boolean>(this.apiUrl + '/delete/' + id);
  }
}
