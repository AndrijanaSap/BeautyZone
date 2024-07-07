import { EmployeeDto } from "./employee.model";
import { HolidayRequestDto } from "./holiday-request-dto.model";

export interface ShiftWithHolidayResponseDto {
  id: number;
  shiftType: string;
  shiftStart: Date;
  shiftEnd: Date;
  holiday:HolidayRequestDto;
  [column: string]: any;
  }

  export class ShiftWithHolidayResponseDto implements ShiftWithHolidayResponseDto{
  
  }