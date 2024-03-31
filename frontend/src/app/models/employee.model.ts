import { ServiceDto } from "./service.model";

export interface EmployeeDto {
    id: number;
    name: string;
    surname: string;
    email: string;
    phone: string;
    services: ServiceDto[];

  }