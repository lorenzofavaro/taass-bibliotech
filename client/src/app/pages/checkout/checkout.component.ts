// /* tslint:disable:no-trailing-whitespace */
// import {AfterViewInit, Component, OnInit} from '@angular/core';
// import {Cart} from '../../models/cart';
// import {Product} from '../../models/product';
// import {CartService} from '../../services/cart.service';
// import {CatalogService} from '../../services/catalog.service';
// import {HttpErrorResponse} from '@angular/common/http';
// import {User} from '../../models/User';
// import {UserService} from '../../services/user.service';
// import {OrderService} from '../../services/order.service';
// import {Order} from '../../models/order';
// import {PaymentService} from '../../services/payment.service';
// import {Router} from '@angular/router';
// import * as Feather from 'feather-icons';
//
// @Component({
//   selector: 'app-checkout',
//   templateUrl: './checkout.component.html',
//   styleUrls: ['./checkout.component.css']
// })
// export class CheckoutComponent implements OnInit, AfterViewInit {
//
//   user: User;
//   cart: Cart;
//   products: Array<Product> = [];
//
//
//   public cardTypeValue;
//   public cardHolderValue;
//   public cardNumberValue;
//   public expiryMonthValue = 0;
//   public expiryYearValue = 0;
//   public cvcValue;
//
//   constructor(private cartService: CartService,
//               private catalogService: CatalogService,
//               private userService: UserService,
//               private orderService: OrderService,
//               private paymentService: PaymentService,
//               private router: Router) { }
//
//   ngOnInit(): void {
//     this.getCart();
//     this.getUser();
//   }
//
//   private getCart(): void {
//     this.cartService.getCart().subscribe(
//       (response: Cart) => {
//         this.cart = response;
//         localStorage.setItem('cartItems', String(this.cart.products.length));
//         this.getProducts();
//       },
//       (error: HttpErrorResponse) => {
//         console.log(error.message);
//       }
//     );
//   }
//
//   private getProducts(): void {
//     this.cart.products.forEach(item => {
//       this.catalogService.getProduct(item.productId).subscribe(
//         (response: Product) => {
//           this.products.push(response);
//         },
//         (error: HttpErrorResponse) => {
//           console.log(error.message);
//         }
//       );
//     });
//   }
//
//   public getUser(): void {
//     this.userService.get().subscribe(
//       (response: User) => {
//         this.user = response;
//       },
//       (error: HttpErrorResponse) => {
//         console.log(error.message);
//       }
//     );
//   }
//
//   getItemQty(id: number): number {
//     return this.cart.products.find(item => item.productId === id).quantity;
//   }
//
//   getItemSize(id: number): number {
//     return this.cart.products.find(item => item.productId === id).size;
//   }
//
//   creditcard(): void {
//     this.createOrder(1);
//   }
//
//   paypal(): void {
//     this.createOrder(2);
//   }
//
//   private createOrder(type: number): void {
//     const productsMap: Map<number, number> = new Map<number, number>();
//     const sizesMap: Map<number, number> = new Map<number, number>();
//
//     this.cart.products.forEach(product => {
//       productsMap.set(product.productId, product.quantity);
//       sizesMap.set(product.productId, product.size);
//     });
//
//     const productsMap2 = {};
//     productsMap.forEach((val: number, key: number) => {
//         productsMap2[key] = val;
//     });
//
//     const sizesMap2 = {};
//     sizesMap.forEach((val: number, key: number) => {
//       sizesMap2[key] = val;
//     });
//
//     const newOrder = {
//       products: productsMap2,
//       sizes: sizesMap2,
//       total: this.cart.grandTotal
//     };
//
//     this.orderService.createOrder(newOrder).subscribe(
//       (order: Order) => {
//         if (type === 1) {
//           this.createPayment(order.id);
//         } else {
//           this.paypalPayment(order.id);
//         }
//       },
//       (error: HttpErrorResponse) => {
//         console.log(error.message);
//       }
//     );
//   }
//
//   private createPayment(order: string): void {
//       const payment = {
//         orderId: order,
//         total: this.cart.grandTotal,
//         cardName: this.cardTypeValue,
//         cardNumber: this.cardNumberValue,
//         expiryMonth: this.expiryMonthValue,
//         expiryYear: this.expiryYearValue,
//         cvc: this.cvcValue,
//         holderName: this.cardHolderValue
//       };
//
//       this.paymentService.payCreditCard(payment).subscribe(result => {
//           //this.cartService.deleteCart().subscribe();
//           this.router.navigate(['/orderSubmitted', { id: order}]);
//         },
//         (error: HttpErrorResponse) => {
//           console.log('errore pagamento');
//         });
//   }
//
//   private paypalPayment(order: string): void {
//     const payment = {
//       orderId: order,
//       total: this.cart.grandTotal,
//     };
//
//     this.paymentService.payPayPal(payment).subscribe(result => {
//       console.log(result);
//       console.log(result.link.href);
//       window.location.href = result.link.href;
//     },
//       (error: HttpErrorResponse) => {
//       console.log('errore paypal');
//       });
//   }
//
//   ngAfterViewInit(): void {
//     Feather.replace();
//   }
// }
