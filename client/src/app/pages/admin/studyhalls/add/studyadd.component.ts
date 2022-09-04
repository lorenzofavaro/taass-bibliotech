/* tslint:disable:no-trailing-whitespace */
import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {CatalogService} from '../../../../services/catalog.service';
import {Product} from '../../../../models/product';
import {HttpErrorResponse} from '@angular/common/http';
import {noop} from 'rxjs';
import {StudyhallsService} from "../../../../services/studyhalls.service";
import {Studyhalls} from "../../../../models/Studyhalls";

@Component({
  selector: 'app-add',
  templateUrl: './studyadd.component.html',
  styleUrls: ['./studyadd.component.css']
})
export class AdminStudyHallsAddComponent {

  constructor(private route: ActivatedRoute,
              private studyhallsService: StudyhallsService,
              private router: Router) { }

  studyhallId: number;
  public studyHall: Studyhalls = {id: 0, name: '', availability: 0, address: ''};

  public error = false;

  addStudyHall(): void {
    const body = {
      name: this.studyHall.name,
      address: this.studyHall.address,
      availability: this.studyHall.availability,
    };

    this.studyhallsService.addStudyHall(body).subscribe(result => {
        this.router.navigate(['/admin/studyhalls']);
      },
      (error: HttpErrorResponse) => {
        this.error = true;
      });
  }
}
