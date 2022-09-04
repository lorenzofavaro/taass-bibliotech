/* tslint:disable:no-trailing-whitespace */
import { Component, OnInit } from '@angular/core';
import {Subscription} from 'rxjs';
import {Router} from '@angular/router';
import {UserService} from '../../services/user.service';
import {UserAuth} from '../../models/UserAuth';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

  currentUserSubrscription: Subscription;
  currentUser: UserAuth;

  constructor(private router: Router,
              private userService: UserService) { }

  ngOnInit(): void {
    this.currentUserSubrscription = this.userService.currentUser.subscribe(user => {
      this.currentUser = user;
    });
  }

  logout(): void {
    this.userService.logout();
    this.router.navigate(['/home']);
  }

  getCartItems(): string {
    return localStorage.getItem('cartItems');
  }
}
