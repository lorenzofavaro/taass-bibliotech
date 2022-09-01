import { Injectable } from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Order} from '../models/order';
import {Payment} from '../models/payment';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public payCreditCard(data): Observable<string> {
    return this.http.post(`${this.apiServerUrl}/payment-service/pay`, data, {responseType: 'text'});
  }

  public payPayPal(data): Observable<any> {
    return this.http.post(`${this.apiServerUrl}/payment-service/paypal`, data);
  }

  public executePayPal(orderId: string, paymentId: string, payerId: string): Observable<any> {
    return this.http.post(`${this.apiServerUrl}/payment-service/paypal/execute/${orderId}/${paymentId}/${payerId}`, {});
  }

  public getPayment(orderId: string): Observable<Payment> {
    return this.http.get<Payment>(`${this.apiServerUrl}/payment-service/payment/${orderId}`);
  }
}
