/* tslint:disable:no-trailing-whitespace */
import {AfterViewInit, Component, OnInit} from '@angular/core';
import {User} from '../../models/User';
import {UserService} from '../../services/user.service';
import {HttpErrorResponse} from '@angular/common/http';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import * as Feather from 'feather-icons';
import {StudyhallsService} from "../../services/studyhalls.service";
import {Order} from "../../models/order";
import {BookingStudyHalls} from "../../models/BookingStudyHalls";
import {Studyhalls} from "../../models/Studyhalls";

@Component({
  selector: 'app-address',
  templateUrl: './address.component.html',
  styleUrls: ['./address.component.css']
})
export class AddressComponent implements OnInit, AfterViewInit {

  public user: User;
  public bookings: BookingStudyHalls[];
  public bookingsstudy: Studyhalls[];
  public studyhalls: Studyhalls[];

  currentDate = new Date();

  constructor(private router: Router,
              private userService: UserService,
              private studyhallsService: StudyhallsService) { }

  ngOnInit(): void {
    this.getUser();
    this.getStudyAllsBooks();
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

  public getStudyAllsBooks(): void {
    this.studyhallsService.getBookings().subscribe(
      (response: BookingStudyHalls[]) => {
        this.bookings = response;
        console.log('dopo call:')
        console.log(this.bookings)
        this.loadStudyhalls();
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
    return this.bookings.sort((a: BookingStudyHalls, b: BookingStudyHalls) => {
      return <any> new Date(b.date) - <any> new Date(a.date);
    });
  }

  public loadStudyhalls(): void {
    this.studyhallsService.getStudyhalls().subscribe(
      (response: Studyhalls[]) => {
        this.studyhalls = response;
      },
      (error: HttpErrorResponse) => {
        console.log(error.message);
      }
    );
  }

  public getStudyHallName(studyHallId: number): string {
    for (var obj of this.studyhalls) {
      if (obj.id == studyHallId) {
          return obj.name
      }
    }
  }

  public getStudyHallAddress(studyHallId: number): string {
    for (var obj of this.studyhalls) {
      if (obj.id == studyHallId) {
        return obj.address
      }
    }
  }

}
