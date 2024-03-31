import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { LoginRequestDto } from 'src/app/models/login-request-dto.model';
import { RegisterRequestDto } from 'src/app/models/register-request-dto.model';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  hide = true;
  registerForm = new FormGroup({
    email: new FormControl("", [
      Validators.required
    ]),
    password: new FormControl("", [
      Validators.required
    ]),
    name: new FormControl("", [
      Validators.required
    ]),
    surname: new FormControl("", [
      Validators.required
    ]),
    phone: new FormControl("", [
      Validators.required
    ]),
  });


  constructor(
    private userService: UserService, private authenticationService: AuthenticationService, private jwtHelper: JwtHelperService, private router: Router) {
  }


  onSubmitRegister() {
    var registerRequestDto: RegisterRequestDto = new RegisterRequestDto();

    if (this.registerForm.controls.email.value)
      registerRequestDto.email = this.registerForm.controls.email.value;

    if (this.registerForm.controls.password.value)
      registerRequestDto.password = this.registerForm.controls.password.value;

    if (this.registerForm.controls.name.value)
      registerRequestDto.name = this.registerForm.controls.name.value;

    if (this.registerForm.controls.surname.value)
      registerRequestDto.surname = this.registerForm.controls.surname.value;

    if (this.registerForm.controls.phone.value)
      registerRequestDto.phone = this.registerForm.controls.phone.value;

    this.authenticationService.register(registerRequestDto).subscribe(response => {
      localStorage.setItem("token", response.token);
      let sub = this.jwtHelper.decodeToken(response.token).sub;
      localStorage.setItem("sub", sub);
      localStorage.setItem('userId', response.userId.toString());
      this.router.navigateByUrl('/');
    });
  }
}
