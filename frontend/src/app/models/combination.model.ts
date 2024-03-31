import { CombinationDtoInterface } from "./interfaces/combination-dto.interface";

export class CombinationDto  implements CombinationDtoInterface {
    startDateTime: Date;
    employeeId: number;
    timeSlotIds: number[];
}
