export class UserDto {
    id: number;
    name: string;
    surname: string;
    email: string;
    phone: string;
    role:string;
    ipAddress:string;
    [column: string]: any;
  }