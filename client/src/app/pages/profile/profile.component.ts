/* tslint:disable:no-trailing-whitespace */
import {AfterViewInit, Component, OnInit} from '@angular/core';
import {User} from '../../models/User';
import {UserService} from '../../services/user.service';
import {HttpErrorResponse} from '@angular/common/http';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import * as Feather from 'feather-icons';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit, AfterViewInit {

  public user: User;
  currentDate = new Date();
  successMsg = false;
  passwordMsg = false;
  errorMsg = false;
  userFormGroup: FormGroup;

  constructor(private router: Router,
              private userService: UserService) { }

  ngOnInit(): void{
    this.getUser();
    this.initUserForm();
  }

  public getUser(): void {
    this.userService.get().subscribe(
      (response: User) => {
        this.user = response;
      },
      (error: HttpErrorResponse) => {
        console.log(error.message);
      }
    );
  }

  updateUser(): void {
    if (this.userFormGroup.value.password === this.userFormGroup.value.passwordConfirm) {

        this.userService.update(this.userFormGroup.value).subscribe(result => {
            this.errorMsg = false;
            this.passwordMsg = false;
            this.successMsg = true;
          },
          (error: HttpErrorResponse) => {
            this.successMsg = false;
            this.passwordMsg = false;
            this.errorMsg = true;
          });
      } else {
      this.successMsg = false;
      this.errorMsg = false;
      this.passwordMsg = true;
      }
  }

  initUserForm(): void {
    this.userFormGroup = new FormGroup({
      firstName: new FormControl('', [Validators.required]),
      lastName: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required]),
      passwordConfirm: new FormControl('', [Validators.required])
    });
  }

  closeMsg(): void {
    this.successMsg = false;
    this.passwordMsg = false;
    this.errorMsg = false;
  }

  logout(): void {
    this.userService.logout();
    this.router.navigate(['/home']);
  }

  ngAfterViewInit(): void {
    Feather.replace();
  }
}
