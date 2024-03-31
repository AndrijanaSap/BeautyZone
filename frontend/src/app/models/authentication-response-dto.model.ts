import { EmployeeDto } from "./employee.model";
import { ServiceDto } from "./service.model";

export class AuthenticationResponseDto {
  token: string;
  userId: number;
  }