import { EmployeeDto } from "./employee.model";
import { ServiceDto } from "./service.model";

export interface AppointmentWithEmployeeResponseDto {
  id: number;
  paymentMethod: string;
  service: ServiceDto;
  employee:EmployeeDto;
  name: string;
  phoneNumber: string;
  email: string;
  note: string | null;
  appointmentDateTime: Date;
  appointmentStatus: string;
  }

  export class AppointmentWithEmployeeResponseDto implements AppointmentWithEmployeeResponseDto{

  }