import { ServiceDto } from "./service.model";

export class AppointmentDto {
  serviceId: number;
  employeeId: number;
  startDateTime: Date;
  endDateTime: Date;
  }