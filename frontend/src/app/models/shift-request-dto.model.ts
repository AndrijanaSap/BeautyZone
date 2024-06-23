export class ShiftRequestDto {
  id: number;
  shiftType: string;
  periodFrom: Date;
  periodTo: Date;
  shiftStart: Date;
  shiftEnd: Date;
  employees:number[];
  }