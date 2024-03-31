import { ServiceDto } from "./service.model";

export interface CategoryDto {
    id: number;
    name: string;
    services: ServiceDto[];
  }