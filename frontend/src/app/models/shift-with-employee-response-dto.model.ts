import { EmployeeDto } from "./employee.model";

export interface ShiftWithEmployeeResponseDto {
  id: number;
  shiftType: string;
  shiftStart: Date;
  shiftEnd: Date;
  employee:EmployeeDto;
  [column: string]: any;
  }

  export class ShiftWithEmployeeResponseDto implements ShiftWithEmployeeResponseDto{
  
  }