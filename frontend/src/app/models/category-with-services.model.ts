import { ServiceDto } from "./service.model";

export interface CategoryWithServicesDto {
    id: number;
    name: string;
    services: ServiceDto[];
  }

  export class CategoryWithServicesDto {
    id: number;
    name: string;
    services: ServiceDto[];
  }