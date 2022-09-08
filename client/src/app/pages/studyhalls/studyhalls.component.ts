/* tslint:disable:no-trailing-whitespace */
import { AfterViewInit, Component, OnInit } from "@angular/core";
import * as Feather from "feather-icons";
import { CatalogService } from "../../services/catalog.service";
import { Router } from "@angular/router";
import { Product } from "../../models/product";
import { HttpErrorResponse } from "@angular/common/http";
import { newArray } from "@angular/compiler/src/util";
import { Studyhalls } from "../../models/Studyhalls";
import { BookingStudyHalls } from "../../models/BookingStudyHalls";
import { StudyhallsService } from "../../services/studyhalls.service";

@Component({
  selector: "app-store",
  templateUrl: "./studyhalls.component.html",
  styleUrls: ["./studyhalls.component.css"],
})
export class StudyhallsComponent implements OnInit, AfterViewInit {
  constructor(
    private studyhallsService: StudyhallsService,
    private router: Router
  ) {}

  public studyhalls: Studyhalls[];
  bookings: BookingStudyHalls[];
  errMsg = false;
  succMsg = false;
  errMsgDisplay = "";

  ngOnInit(): void {
    this.loadStudyHallsBookings();
    console.log(this.bookings);
    this.loadStudyhalls();
  }

  ngAfterViewInit(): void {
    Feather.replace();
  }

  public loadStudyhalls(): void {
    this.studyhallsService.getStudyhalls().subscribe(
      (response: Studyhalls[]) => {
        this.studyhalls = response;
        this.studyhalls.forEach(studyHall => {
          if (this.bookings.some(x => x.studyHallId === studyHall.id)){
            (studyHall as any).booked = true;
          } else {
            (studyHall as any).booked = false;
          }
        });
      },
      (error: HttpErrorResponse) => {
        console.log(error.message);
      });
  }

  public loadStudyHallsBookings(): void {
    this.studyhallsService.getTodayBookings().subscribe(
      (response: BookingStudyHalls[]) => {
        this.bookings = response;
      },
      (error: HttpErrorResponse) => {
        console.log(error.message);
      }
    );
  }

  public bookStudyHall(studyIndex: number): void {
    var studyHall = this.studyhalls[studyIndex];
    if (studyHall.availability < 1) {
      this.succMsg = false;
      this.errMsg = true;
      this.errMsgDisplay = "unavailable";
    } else {
      this.studyhallsService.bookStudyHall(studyHall.id).subscribe(
        (result) => {
          if (result == null) {
            this.errMsg = true;
            this.succMsg = false;
            this.errMsgDisplay = "already_booked";
          } else {
            this.errMsg = false;
            this.succMsg = true;
            this.studyhalls[studyIndex].availability--;
          }
        },
        (error: HttpErrorResponse) => {
          this.succMsg = false;
          this.errMsg = true;
          if (error.status == 401) {
            // Unauthorized
            this.router.navigate(["/login"]);
          }
        }
      );
    }
  }
}
