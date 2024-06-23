import { ServiceDto } from "./service.model";
import { UserDto } from "./user.model";

export interface AppointmentWithClientResponseDto {
  id: number;
  paymentMethod: string;
  service: ServiceDto;
  client:UserDto;
  name: string;
  phoneNumber: string;
  email: string;
  note: string | null;
  appointmentDateTime: Date;
  appointmentStatus: string;
  }

  export class AppointmentWithClientResponseDto implements AppointmentWithClientResponseDto{

  }