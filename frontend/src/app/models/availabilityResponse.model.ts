import { CombinationDto } from "./combination.model";

export class AvailabilityResponseDto {
    date: Date;
    combinationDtos: CombinationDto[];
}
