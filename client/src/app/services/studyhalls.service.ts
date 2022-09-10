/* tslint:disable:no-trailing-whitespace */
import { Injectable } from '@angular/core';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs';
import {Product} from '../models/product';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Studyhalls} from "../models/Studyhalls";
import {Order} from "../models/order";
import {BookingStudyHalls} from "../models/BookingStudyHalls";

@Injectable({
  providedIn: 'root'
})
export class StudyhallsService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public getStudyhalls(): Observable<Studyhalls[]> {
    return this.http.get<Studyhalls[]>(`${this.apiServerUrl}/studyhalls-service/studyhalls/all`);
  }

  public getStudyhallsAdmin(): Observable<Studyhalls[]> {
    return this.http.get<Studyhalls[]>(`${this.apiServerUrl}/studyhalls-service/studyhalls/admin_all`);
  }

  public getMostAvailableStudyHalls(count: number): Observable<Studyhalls[]> {
    return this.http.get<Studyhalls[]>(`${this.apiServerUrl}/studyhalls-service/studyhalls/most_available/${count}`);
  }

  public addStudyHall(data): Observable<string> {
    return this.http.post(`${this.apiServerUrl}/studyhalls-service/studyhalls/add`, data, {responseType: 'text'});
  }

  public deleteStudyHall(id: number): Observable<string> {
    return this.http.delete(`${this.apiServerUrl}/studyhalls-service/studyhalls/delete/${id}`, {responseType: 'text'});
  }

  public getStudyhall(id: number): Observable<Studyhalls> {
    return this.http.get<Studyhalls>(`${this.apiServerUrl}/studyhalls-service/studyhalls/${id}`);
  }

  public editStudyhall(data): Observable<string> {
    return this.http.post(`${this.apiServerUrl}/studyhalls-service/studyhalls/edit`, data, {responseType: 'text'});
  }

  public getBookings(): Observable<BookingStudyHalls[]> {
    return this.http.get<BookingStudyHalls[]>(`${this.apiServerUrl}/studyhalls-service/studyhalls/bookings`);
  }

  public getTodayBookings(): Observable<BookingStudyHalls[]> {
    return this.http.get<BookingStudyHalls[]>(`${this.apiServerUrl}/studyhalls-service/studyhalls/today_bookings`);
  }

  public bookStudyHall(id: number): Observable<BookingStudyHalls> {
    return this.http.get<BookingStudyHalls>(`${this.apiServerUrl}/studyhalls-service/studyhalls/create/${id}`);
  }

  public cancelBooking(id: number): Observable<Boolean> {
    return this.http.delete<Boolean>(`${this.apiServerUrl}/studyhalls-service/studyhalls/cancel_booking/${id}`);
  }
}
