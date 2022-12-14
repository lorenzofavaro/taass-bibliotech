/* tslint:disable:no-trailing-whitespace */
import {AfterViewInit, Component, OnInit} from '@angular/core';
import {ActivatedRoute, Route, Router} from '@angular/router';
import {Order} from '../../models/order';
import {OrderService} from '../../services/order.service';
import {Product} from '../../models/product';
import {HttpErrorResponse} from '@angular/common/http';
import {CatalogService} from '../../services/catalog.service';
import {UserService} from '../../services/user.service';
import {User} from '../../models/User';
import * as Feather from 'feather-icons';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit, AfterViewInit {

  orderId: string;
  order: Order;
  products: Array<Product> = [];
  public user: User;
  currentDate = new Date();
  shippingDate = new Date();

  constructor(private route: ActivatedRoute,
              private router: Router,
              private orderService: OrderService,
              private catalogService: CatalogService,
              private userService: UserService) { }

  ngOnInit(): void {
    this.shippingDate.setDate(this.currentDate.getDate() + 30);
    this.getUser();
    this.orderId = this.route.snapshot.paramMap.get('id');
    this.getOrder();
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

  private reloadCurrentRoute() {
      let currentUrl = this.router.url;
      this.router.navigateByUrl('/', {skipLocationChange: true}).then(() => {
          this.router.navigate([currentUrl]);
      });
  }

  public returnOrder(): void {
    this.orderService.returnOrder(this.orderId).subscribe(
      (response: Boolean) => {
        console.log(response);
        this.reloadCurrentRoute();
      },
      (error: HttpErrorResponse) => {
        console.log(error.message);
      });
  }

  public cancelOrder(): void {
    this.orderService.cancelOrder(this.orderId).subscribe(
      (response: Boolean) => {
        console.log(response);
        this.reloadCurrentRoute();
      },
      (error: HttpErrorResponse) => {
        console.log(error.message);
      });
  }

  private getOrder(): void {
    this.orderService.getOrder(this.orderId).subscribe(
      (response: Order) => {
        this.order = response;
        this.getProducts();
        console.log(this.order);
      },
      (error: HttpErrorResponse) => {
        console.log(error.message);
      }
    );
  }

  private getProducts(): void {
    this.order.products.forEach(product => {
      this.catalogService.getProduct(product.productId).subscribe(
        (response: Product) => {
          this.products.push(response);
        },
        (error: HttpErrorResponse) => {
          console.log(error.message);
        }
      );
    });
    console.log(this.products);
  }

  logout(): void {
    this.userService.logout();
    this.router.navigate(['/home']);
  }

  ngAfterViewInit(): void {
    Feather.replace();
  }
}
