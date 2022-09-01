/* tslint:disable:no-trailing-whitespace */
import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Route} from '@angular/router';
import {PaymentService} from '../../services/payment.service';
import {CartService} from '../../services/cart.service';

@Component({
  selector: 'app-order-submitted',
  templateUrl: './order-submitted.component.html',
  styleUrls: ['./order-submitted.component.css']
})
export class OrderSubmittedComponent implements OnInit {

  constructor(private route: ActivatedRoute,
              private paymentService: PaymentService,
              private cartService: CartService) { }

  public orderId: string;
  paymentId: string;
  payerId: string;
  public shippingDate = new Date();

  ngOnInit(): void {
    this.shippingDate.setDate(new Date().getDate() + 4);
    this.orderId = this.route.snapshot.paramMap.get('id');
    this.route.queryParams.subscribe(params => {
      this.paymentId = params['paymentId'];
      this.payerId = params['PayerID'];
    });

    if (this.paymentId !== null && this.payerId != null) {
      console.log('dent');
      this.paymentService.executePayPal(this.orderId, this.paymentId, this.payerId).subscribe();
    }

    this.cartService.deleteCart().subscribe();
    localStorage.setItem('cartItems', String(0));
  }

}
