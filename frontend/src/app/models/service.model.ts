import { CategoryDto } from "./category.model";

export interface ServiceDto {
    id: number;
    name: string;
    description: string;
    imgPath: string;
    durationInMinutes: number;
    price: number;
    category: CategoryDto;
  }

  export class ServiceDto {
    id: number;
    name: string;
    description: string;
    imgPath: string;
    durationInMinutes: number;
    price: number;
    category: CategoryDto;
  }