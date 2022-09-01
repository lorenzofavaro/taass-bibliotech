/* tslint:disable:no-trailing-whitespace */
import {AfterViewInit, Component, OnInit} from '@angular/core';
import * as Feather from 'feather-icons';
import {CatalogService} from '../../services/catalog.service';
import {Product} from '../../models/product';
import {HttpErrorResponse} from '@angular/common/http';
import {newArray} from '@angular/compiler/src/util';
import {Studyhalls} from "../../models/Studyhalls";
import {StudyhallsService} from "../../services/studyhalls.service";

@Component({
  selector: 'app-store',
  templateUrl: './studyhalls.component.html',
  styleUrls: ['./studyhalls.component.css']
})
export class StudyhallsComponent implements OnInit, AfterViewInit {

  constructor(private studyhallsService: StudyhallsService) { }

  public studyhalls: Studyhalls[];
  errMsg = false;
  succMsg = false;

  ngOnInit(): void {
    this.loadStudyhalls();
  }

  ngAfterViewInit(): void {
    Feather.replace();
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
  public bookStudyHall(studyIndex: number): void {
    var studyHall = this.studyhalls[studyIndex]
    if (studyHall.availability < 1) {
      this.succMsg = false;
      this.errMsg = true;
    } else {
      this.studyhallsService.bookStudyHall(studyHall.id).subscribe(result => {
          this.errMsg = false;
          this.succMsg = true;
          this.studyhalls[studyIndex].availability--;
        },
        (error: HttpErrorResponse) => {
          this.succMsg = false;
          this.errMsg = true;
        }
      )
    }
  }
}
