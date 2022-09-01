/* tslint:disable:no-trailing-whitespace */
import { Injectable } from '@angular/core';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs';
import {Product} from '../models/product';
import {Cart} from '../models/cart';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public getCart(): Observable<Cart> {
    return this.http.get<Cart>(`${this.apiServerUrl}/cart-service/cart`);
  }

  public addProduct(data): Observable<string> {
    return this.http.post(`${this.apiServerUrl}/cart-service/cart`, data, {responseType: 'text'});
  }

  public deleteProduct(data): Observable<string> {
    return this.http.post(`${this.apiServerUrl}/cart-service/product`, data, {responseType: 'text'});
  }

  public deleteCart(): Observable<string> {
    return this.http.delete(`${this.apiServerUrl}/cart-service/cart`, {responseType: 'text'});
  }
}
