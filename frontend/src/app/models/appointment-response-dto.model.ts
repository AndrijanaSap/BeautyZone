import { EmployeeDto } from "./employee.model";
import { ServiceDto } from "./service.model";

export interface AppointmentResponseDto {
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

  export class AppointmentResponseDto implements AppointmentResponseDto{

  }