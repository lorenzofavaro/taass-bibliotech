/* tslint:disable:no-trailing-whitespace */
import {AfterViewInit, Component, OnInit} from '@angular/core';
import {UserService} from '../../services/user.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {HttpErrorResponse} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import { SocialAuthService, GoogleLoginProvider, SocialUser } from 'angularx-social-login';
import * as Feather from 'feather-icons';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit, AfterViewInit {

  formGroup: FormGroup;
  signupForm: FormGroup;
  socialUser: SocialUser;
  isLoginError = false;
  signupSuccess = false;
  returnUrl = '/';

  constructor(
    private authService: UserService,
    private route: ActivatedRoute,
    private router: Router,
    private socialAuthService: SocialAuthService
  ) { }

  ngOnInit(): void {
    this.initForm();
    this.initSignupForm();
    const params = this.route.snapshot.queryParamMap;
    this.returnUrl = params.get('returnUrl');

    this.socialAuthService.authState.subscribe((user) => {
      this.socialUser = user;
      console.log(this.socialUser);
    });
  }

  login(): void {
    if (this.formGroup.valid) {
      this.authService.login(this.formGroup.value).subscribe(result => {
        console.log(result);
        this.router.navigateByUrl(this.returnUrl);
      },
        (error: HttpErrorResponse)  => {
        console.log(error);
        this.isLoginError = true;
        });
    }
  }

  loginWithGoogle(): void {
    this.socialAuthService.signIn(GoogleLoginProvider.PROVIDER_ID).then((userData) => {
      console.log(userData.email);
      const body = {
        email: userData.email,
        firstName: userData.firstName,
        lastName: userData.lastName,
      };
      this.authService.loginWithGoogle(body).subscribe(result => {
        console.log(result);
        this.router.navigateByUrl(this.returnUrl);
      },
        (error: HttpErrorResponse)  => {
          console.log(error);
          this.isLoginError = true;
        });

    }, (error: any) => {
      console.log(error);
    });
  }

  signup(): void {
    if (this.signupForm.valid) {
      this.authService.signup(this.signupForm.value).subscribe(result => {
          console.log(result);
          this.signupSuccess = true;
        },
        (error: HttpErrorResponse)  => {
          console.log(error);
          this.isLoginError = true;
        });
    }
  }

  initForm(): void {
    this.formGroup = new FormGroup({
      email: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required])
    });
  }

  initSignupForm(): void {
    this.signupForm = new FormGroup({
      email: new FormControl('', [Validators.required]),
      firstName: new FormControl('', [Validators.required]),
      lastName: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required])
    });
  }

  ngAfterViewInit(): void {
    Feather.replace();
  }
}
