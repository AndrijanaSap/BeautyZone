import { EmployeeDto } from "./employee.model";
import { ServiceDto } from "./service.model";

export interface AppointmentWithEmployeeResponseDto {
  id: number;
  clientId:string;
  paymentMethod: string;
  service: ServiceDto;
  employee:EmployeeDto;
  name: string;
  phoneNumber: string;
  email: string;
  note: string | null;
  appointmentDateTime: Date;
  appointmentStatus: string;
  ipAddress: string;
  [column: string]: any;
  }

  export class AppointmentWithEmployeeResponseDto implements AppointmentWithEmployeeResponseDto{
  
  }