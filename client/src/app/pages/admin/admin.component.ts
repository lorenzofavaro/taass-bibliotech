/* tslint:disable:no-trailing-whitespace */
import {Component, OnDestroy, OnInit} from '@angular/core';
import {Product} from '../../models/product';
import {CatalogService} from '../../services/catalog.service';
import {HttpErrorResponse} from '@angular/common/http';
import {Subject} from 'rxjs';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit, OnDestroy {

  public products: Product[] = [];
  dtOptions: DataTables.Settings = {};
  dtTrigger: Subject<any> = new Subject<any>();

  constructor(private catalogService: CatalogService) { }

  ngOnInit(): void {
    this.dtOptions = {
      pagingType: 'simple_numbers',
      language: {
        processing: "Caricamento in corso...",
        search: "Cerca:",
        lengthMenu: "Mostra _MENU_ prodotti",
        info: "Prodotti dal _START_ al _END_ (_TOTAL_ prodotti totali)",
        infoEmpty: "Nessun prodotto.",
        zeroRecords: "La ricerca non ha prodotto risultati\n",
        emptyTable: "Nessun prodotto.",
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
    this.catalogService.getProducts().subscribe(
      (response: Product[]) => {
        this.products = response;
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
    this.catalogService.deleteProduct(id).subscribe(result => {
        this.products = this.products.filter(item => item.id !== id);
      },
      (error: HttpErrorResponse) => {
        console.log(error);
      }
    );
  }
}
