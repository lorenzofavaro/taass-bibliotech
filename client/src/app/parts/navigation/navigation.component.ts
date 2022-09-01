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
      if (!user || user.roles === 'ROLE_ADMIN') {

      }
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



/*import {Component, OnDestroy, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";
import {Subscription} from "rxjs";
import {JwtResponse} from "../../response/JwtResponse";
import {Router} from "@angular/router";
import {Role} from "../../enum/Role";

@Component({
    selector: 'app-navigation',
    templateUrl: './navigation.component.html',
    styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit, OnDestroy {


    currentUserSubscription: Subscription;
    name$;
    name: string;
    currentUser: JwtResponse;
    root = '/';
    Role = Role;


    ngOnInit() {
        this.name$ = this.userService.name$.subscribe(aName => this.name = aName);
        this.currentUserSubscription = this.userService.currentUser.subscribe(user => {
            this.currentUser = user;
            if (!user || user.role == Role.Customer) {
                this.root = '/';
            } else {
                this.root = '/seller';
            }
        });
    }




}*/
