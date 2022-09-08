import { Injectable } from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Product} from '../models/product';
import {Order} from '../models/order';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public getOrders(): Observable<Order[]> {
    return this.http.get<Order[]>(`${this.apiServerUrl}/booking-service/all`);
  }

  public getOrder(id: string): Observable<Order> {
    return this.http.get<Order>(`${this.apiServerUrl}/booking-service/${id}`);
  }

  public getOrderFromProductId(productId: number): Observable<Order> {
    return this.http.get<Order>(`${this.apiServerUrl}/booking-service/product/${productId}`);
  }

  public createOrder(data): Observable<Order> {
    return this.http.post<Order>(`${this.apiServerUrl}/booking-service/create`, data);
  }
}
