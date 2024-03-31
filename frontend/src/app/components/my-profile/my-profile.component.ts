import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { UserDto } from 'src/app/models/user.model';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-my-profile',
  templateUrl: './my-profile.component.html',
  styleUrls: ['./my-profile.component.css']
})
export class MyProfileComponent implements OnInit {
setEditMode() {
  this.editMode = true;
  this.userUpdateForm.controls.name.enable();
  this.userUpdateForm.controls.surname.enable();
  this.userUpdateForm.controls.phone.enable ();
}

  editMode : boolean;
  userUpdateForm = new FormGroup({
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
    id: new FormControl(0),
  });


  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.editMode = false;
    var id = localStorage.getItem('userId');
    if (id)
      this.userService.getUserById(id).subscribe(data => {
        if (data.name) this.userUpdateForm.controls.name.setValue(data.name);
        if (data.surname) this.userUpdateForm.controls.surname.setValue(data.surname);
        if (data.email) this.userUpdateForm.controls.email.setValue(data.email);
        if (data.phone) this.userUpdateForm.controls.phone.setValue(data.phone);
        if (data.id) this.userUpdateForm.controls.id.setValue(data.id);
        this.userUpdateForm.disable();
      });
  }


  onSubmitUpdate() {
    var updateUserDto: UserDto = new UserDto();

    if (this.userUpdateForm.controls.email.value)
      updateUserDto.email = this.userUpdateForm.controls.email.value;

    if (this.userUpdateForm.controls.name.value)
      updateUserDto.name = this.userUpdateForm.controls.name.value;

    if (this.userUpdateForm.controls.surname.value)
      updateUserDto.surname = this.userUpdateForm.controls.surname.value;

    if (this.userUpdateForm.controls.phone.value)
      updateUserDto.phone = this.userUpdateForm.controls.phone.value;

      if (this.userUpdateForm.controls.id.value)
      updateUserDto.id = this.userUpdateForm.controls.id.value;

    this.userService.updateUser(updateUserDto).subscribe(data => {
      if (data.name) this.userUpdateForm.controls.name.setValue(data.name);
      if (data.surname) this.userUpdateForm.controls.surname.setValue(data.surname);
      if (data.email) this.userUpdateForm.controls.email.setValue(data.email);
      if (data.phone) this.userUpdateForm.controls.phone.setValue(data.phone);
    });
    this.editMode= false;
  }


}

