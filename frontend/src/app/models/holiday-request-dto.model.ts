export class HolidayRequestDto {
  id: number;
  startDateTime: Date;
  endDateTime: Date;
  name: string;
  holidayType: string;
  employees:number[];
  }