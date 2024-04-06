import { CategoryDto } from "./category.model";

export class UpdateCategoryWithServicesDto extends CategoryDto{
    services:number[];
  }