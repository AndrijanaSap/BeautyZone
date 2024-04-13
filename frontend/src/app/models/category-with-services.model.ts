import { ServiceDto } from "./service.model";

export interface CategoryWithServicesDto {
    id: number;
    name: string;
    jobPosition: string;
    services: ServiceDto[];
    [column: string]: any;
  }

  export class CategoryWithServicesDto {
    id: number;
    name: string;
    jobPosition: string;
    services: ServiceDto[];
  }