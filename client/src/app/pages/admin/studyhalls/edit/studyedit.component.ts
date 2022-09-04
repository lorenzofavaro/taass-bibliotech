/* tslint:disable:no-trailing-whitespace */
import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpErrorResponse} from '@angular/common/http';
import {noop} from 'rxjs';
import {StudyhallsService} from "../../../../services/studyhalls.service";
import {Studyhalls} from "../../../../models/Studyhalls";

@Component({
  selector: 'app-edit',
  templateUrl: './studyedit.component.html',
  styleUrls: ['./studyedit.component.css']
})
export class AdminStudyHallsEditComponent implements OnInit {

  constructor(private route: ActivatedRoute,
              private studyhallsService: StudyhallsService,
              private router: Router) { }

  studyHallId: number;
  studyhall: Studyhalls;

  public error = false;

  public productCategories: string[] = [];

  ngOnInit(): void {
    this.studyHallId = Number(this.route.snapshot.paramMap.get('id'));
    this.getStudyHall();
  }

  private getStudyHall(): void {
    this.studyhallsService.getStudyhall(this.studyHallId).subscribe(
      (response: Studyhalls) => {
        this.studyhall = response;
      },
      (error: HttpErrorResponse) => {
        console.log(error.message);
      }
    );
  }

  editStudyHall(): void {
    const body = {
      id: this.studyhall.id,
      name: this.studyhall.name,
      address: this.studyhall.address,
      availability: this.studyhall.availability,
    };

    this.studyhallsService.editStudyhall(body).subscribe(result => {
        this.router.navigate(['/admin/studyhalls']);
      },
      (error: HttpErrorResponse) => {
        this.error = true;
        console.log('errore add');
      });
  }
}
