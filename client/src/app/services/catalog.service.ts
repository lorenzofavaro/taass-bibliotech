/* tslint:disable:no-trailing-whitespace */
import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Product} from '../models/product';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})

export class CatalogService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiServerUrl}/catalog-service/catalog/allCatalog`);
  }

  public getFeatured(): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiServerUrl}/catalog-service/catalog/featured`);
  }

  public getProduct(id: number): Observable<Product> {
    return this.http.get<Product>(`${this.apiServerUrl}/catalog-service/catalog/${id}`);
  }

  public getBrands(): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiServerUrl}/catalog-service/catalog/brands`);
  }

  public getCategories(): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiServerUrl}/catalog-service/catalog/categories`);
  }

  public getSizs(): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiServerUrl}/catalog-service/catalog/sizes`);
  }

  public getFilterProducts(data): Observable<Product[]> {
    return this.http.post<Product[]>(`${this.apiServerUrl}/catalog-service/catalog/catalog`, data);
  }

  public deleteProduct(id: number): Observable<string> {
    return this.http.delete(`${this.apiServerUrl}/catalog-service/product/delete/${id}`, {responseType: 'text'});
  }

  public addProduct(data): Observable<string> {
    return this.http.post(`${this.apiServerUrl}/catalog-service/product/add`, data, {responseType: 'text'});
  }

  public editProduct(data): Observable<string> {
    return this.http.post(`${this.apiServerUrl}/catalog-service/product/edit`, data, {responseType: 'text'});
  }
}
