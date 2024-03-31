export class CreateAppointmentRequestDto {
  timeSlotIds: number[];
  
  //appointment data
  paymentMethod: string;
  serviceId: number;
  employeeId:number;
  name: string;
  phoneNumber: string;
  email: string;
  note: string | null;
  }