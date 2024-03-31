import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { LoginRequestDto } from 'src/app/models/login-request-dto.model';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  hide = true;
  loginForm = new FormGroup({
    email: new FormControl("", [
      Validators.required
    ]),
    password: new FormControl("", [
      Validators.required
    ]),
  });

  
  constructor(
    private userService: UserService, private authenticationService: AuthenticationService, private jwtHelper: JwtHelperService, private router: Router) {
  }


  onSubmitLogin() {
    var loginRequestDto: LoginRequestDto = new LoginRequestDto();
  
      if (this.loginForm.controls.email.value)
        loginRequestDto.email = this.loginForm.controls.email.value;
  
      if (this.loginForm.controls.password.value)
        loginRequestDto.password = this.loginForm.controls.password.value;

        this.authenticationService.login(loginRequestDto).subscribe(response => {
          localStorage.setItem("token", response.token);
          let sub = this.jwtHelper.decodeToken(response.token).sub;
          localStorage.setItem("sub", sub);
          localStorage.setItem('userId', response.userId.toString());
          this.router.navigateByUrl('/');
        });
  }
  
}
