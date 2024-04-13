import { ServiceDto } from "./service.model";

export class AppointmentRequestDto {
  serviceId: number;
  employeeId: number;
  periodFrom: Date;
  periodTo: Date;
  includeAppointmentId: number;
  }