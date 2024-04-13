import { CombinationDto } from "./combination.model";
import { TimeSlot } from "./timeSlot.model";

export class DaySlot {
    date: string;
    selected: boolean;
    timeSlots: TimeSlot[];

    constructor(date:string, timeSlots: TimeSlot[]) {
        this.date = date;
        this.timeSlots = timeSlots
    }
}
