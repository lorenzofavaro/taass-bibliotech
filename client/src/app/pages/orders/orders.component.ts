/* tslint:disable:no-trailing-whitespace */
import {AfterViewInit, Component, OnInit} from '@angular/core';
import {User} from '../../models/User';
import {UserService} from '../../services/user.service';
import {HttpErrorResponse} from '@angular/common/http';
import {Order} from '../../models/order';
import {OrderService} from '../../services/order.service';
import {Router} from '@angular/router';
import * as Feather from 'feather-icons';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit, AfterViewInit {

  public user: User;
  public orders: Order[];
  currentDate = new Date();

  constructor(private router: Router,
              private userService: UserService,
              private orderService: OrderService) { }


  ngOnInit(): void {
    this.getUser();
    this.getOrders();
  }

  public getOrders(): void {
    this.orderService.getOrders().subscribe(
      (response: Order[]) => {
        this.orders = response;
      },
      (error: HttpErrorResponse) => {
        console.log(error.message);
      }
    );
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

  logout(): void {
    this.userService.logout();
    this.router.navigate(['/home']);
  }

  ngAfterViewInit(): void {
    Feather.replace();
  }

  get sortData(): any {
    return this.orders.sort((a: Order, b: Order) => {
      return <any> new Date(b.date) - <any> new Date(a.date);
    });
  }
}
