import { EmployeeDto } from "./employee.model";
import { ServiceDto } from "./service.model";

export class UpdateServiceWithEmployeesDto extends ServiceDto{
    employees:number[];
    categoryId:number;
  }