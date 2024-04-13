// import { HttpClient } from '@angular/common/http';
import { Component, ElementRef, ViewChild, inject } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, startWith, map, catchError, tap, throwError } from 'rxjs';
import { UserDto } from 'src/app/models/user.model';
import { UserService } from 'src/app/services/user.service';


@Component({
  selector: 'app-client-edit',
  templateUrl: './client-edit.component.html',
  styleUrls: ['./client-edit.component.css']
})
export class ClientEditComponent {
  hide = true;
  updateForm = new FormGroup({
    id: new FormControl(0, [
      Validators.required
    ]),
    email: new FormControl("", [
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
  ipv4: any;
  constructor(private userService: UserService, private router: Router, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.userService.getIp().subscribe(data => this.ipv4 = data.IPv4);
    const userId = this.route.snapshot.paramMap.get('id');
    if (userId) {
      this.userService.getUserById(userId).subscribe(data => {
        if (data.id) this.updateForm.controls.id.setValue(data.id);
        if (data.name) this.updateForm.controls.name.setValue(data.name);
        if (data.surname) this.updateForm.controls.surname.setValue(data.surname);
        if (data.email) this.updateForm.controls.email.setValue(data.email);
        if (data.phone) this.updateForm.controls.phone.setValue(data.phone);
      });
    }

  //   this.http.get<any>('https://geolocation-db.com/json/')
  // .pipe(
  //   catchError(err => {
  //     return throwError(err);
  //   }),
  //   tap(response => {
  //     console.log(response.IPv4);
  //   })
  // )
  }

  onSubmit() {
    var userDto: UserDto = new UserDto();

    if (this.updateForm.controls.id.value)
      userDto.id = this.updateForm.controls.id.value;

    if (this.updateForm.controls.email.value)
      userDto.email = this.updateForm.controls.email.value;

    if (this.updateForm.controls.name.value)
      userDto.name = this.updateForm.controls.name.value;

    if (this.updateForm.controls.surname.value)
      userDto.surname = this.updateForm.controls.surname.value;

    if (this.updateForm.controls.phone.value)
      userDto.phone = this.updateForm.controls.phone.value;

      userDto.role = "CLIENT";
      userDto.ipAddress = this.ipv4;
  
    this.userService.updateUser(userDto).subscribe(response => {
      this.router.navigateByUrl('/clients');
    });
  }
}
