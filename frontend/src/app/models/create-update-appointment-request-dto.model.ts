export class CreateUpdateAppointmentRequestDto {
  timeSlotIds: number[];
  
  //appointment data
  id: number;
  paymentMethod: string;
  serviceId: number;
  employeeId:number;
  name: string;
  phoneNumber: string;
  email: string;
  note: string | null;
  }