import { CategoryDto } from "./category.model";
import { EmployeeDto } from "./employee.model";
import { ServiceDto } from "./service.model";

export interface ServiceWithEmployeesDto extends ServiceDto {
  category: CategoryDto;
  employees: EmployeeDto[];
}