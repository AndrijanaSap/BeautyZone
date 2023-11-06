import { ServiceDto } from "./service-dto.model";

export interface CategoryDto {
    id: number;
    name: string;
    durationInMinutes: number;
    price: number;
    services: ServiceDto[];
  }