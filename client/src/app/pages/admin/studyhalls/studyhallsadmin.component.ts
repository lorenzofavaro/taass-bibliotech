/* tslint:disable:no-trailing-whitespace */
import {Component, OnDestroy, OnInit} from '@angular/core';
import {HttpErrorResponse} from '@angular/common/http';
import {Subject} from 'rxjs';
import {StudyhallsService} from "../../../services/studyhalls.service";
import {Studyhalls} from "../../../models/Studyhalls";

@Component({
  selector: 'app-admin',
  templateUrl: './studyhallsadmin.component.html',
  styleUrls: ['./studyhallsadmin.component.css']
})

export class AdminStudyHallsComponent implements OnInit, OnDestroy {

  public studyhalls: Studyhalls[] = [];
  dtOptions: DataTables.Settings = {};
  dtTrigger: Subject<any> = new Subject<any>();

  constructor(private studyhallsService: StudyhallsService) { }

  ngOnInit(): void {
    this.dtOptions = {
      pagingType: 'simple_numbers',
      language: {
        processing: "Caricamento in corso...",
        search: "Cerca:",
        lengthMenu: "Mostra _MENU_ aule studio",
        info: "Aule studio dal _START_ al _END_ (_TOTAL_)",
        infoEmpty: "Nessun prodotto.",
        zeroRecords: "La ricerca non ha prodotto risultati\n",
        emptyTable: "Nessuna auala studio.",
        paginate: {
          first: "Primo",
          previous: "Precedente",
          next: "Successivo",
          last: "Ultimo"
        },
        aria: {
          sortAscending: ": Activar para ordenar la tabla en orden ascendente",
          sortDescending: ": Activar para ordenar la tabla en orden descendente"
        }
      }
    };
    this.getProducts();
  }

  public getProducts(): void {
    this.studyhallsService.getStudyhalls().subscribe(
      (response: Studyhalls[]) => {
        this.studyhalls = response;
        this.dtTrigger.next();
      },
      (error: HttpErrorResponse) => {
        console.log(error.message);
      }
    );
  }

  ngOnDestroy(): void {
    this.dtTrigger.unsubscribe();
  }

  delete(id: number): void {
    console.log(id);
    this.studyhallsService.deleteStudyHall(id).subscribe(result => {
        this.studyhalls = this.studyhalls.filter(item => item.id !== id);
      },
      (error: HttpErrorResponse) => {
        console.log(error);
      }
    );
  }
}
