// /* tslint:disable:no-trailing-whitespace */
// import {AfterViewInit, Component, OnInit} from '@angular/core';
// import {HttpErrorResponse} from '@angular/common/http';
// import {CartService} from '../../services/cart.service';
// import {Cart} from '../../models/cart';
// import {Product} from '../../models/product';
// import {CatalogService} from '../../services/catalog.service';
// import * as Feather from 'feather-icons';
//
// @Component({
//   selector: 'app-cart',
//   templateUrl: './cart.component.html',
//   styleUrls: ['./cart.component.css']
// })
// export class CartComponent implements OnInit, AfterViewInit {
//
//   cart: Cart;
//   products: Array<Product> = [];
//
//   constructor(private cartService: CartService,
//               private catalogService: CatalogService) { }
//
//   ngOnInit(): void {
//     this.getCart();
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
//     console.log(this.products);
//   }
//
//   removeItem(id: number): void {
//     const cartItem = this.cart.products.find(item => item.productId === id);
//     this.cartService.deleteProduct(cartItem).subscribe(response => {
//         this.products = this.products.filter(item => item.id !== id);
//         localStorage.setItem('cartItems', String(this.products.length));
//       },
//         (error: HttpErrorResponse) => {
//           console.log(error.message);
//         });
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
//   ngAfterViewInit(): void {
//     Feather.replace();
//   }
// }
