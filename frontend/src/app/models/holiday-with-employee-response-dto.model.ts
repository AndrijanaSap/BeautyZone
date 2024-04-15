import { EmployeeDto } from "./employee.model";

export interface HolidayWithEmployeeResponseDto {
  id: number;
  startDateTime: Date;
  endDateTime: Date;
  name: string;
  holidayType: string;
  employee:EmployeeDto;
  [column: string]: any;
  }

  export class HolidayWithEmployeeResponseDto implements HolidayWithEmployeeResponseDto{
  
  }