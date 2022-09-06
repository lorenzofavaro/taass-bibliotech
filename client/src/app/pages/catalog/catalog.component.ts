/* tslint:disable:no-trailing-whitespace */
import {AfterViewInit, Component, OnInit} from '@angular/core';
import * as Feather from 'feather-icons';
import {CatalogService} from '../../services/catalog.service';
import {Product} from '../../models/product';
import {HttpErrorResponse} from '@angular/common/http';
import {newArray} from '@angular/compiler/src/util';

@Component({
  selector: 'app-store',
  templateUrl: './catalog.component.html',
  styleUrls: ['./catalog.component.css']
})
export class CatalogComponent implements OnInit, AfterViewInit {

  constructor(private catalogService: CatalogService) { }

  public products: Product[];
  public categories: string[];

  public categoriesFilter: string[] = [];
  public searchFilter: string;
  public orderFilter: string;

  ngOnInit(): void {
    this.getCategories();
    this.loadProducts();
  }

  ngAfterViewInit(): void {
    Feather.replace();
  }

  getCategories(): void {
    this.catalogService.getCategories().subscribe(
      (response: string[]) => {
        this.categories = response;
      },
      (error: HttpErrorResponse) => {
        console.log(error.message);
      }
    );
  }

  public loadProducts(): void {
    this.catalogService.getFilterProducts({}).subscribe(
      (response: Product[]) => {
        this.products = response;
      },
      (error: HttpErrorResponse) => {
        console.log(error.message);
      }
    );
  }

  filterProducts(): void {

    const filters = {
      category: this.categoriesFilter,
      sort: this.orderFilter,
      //page: 0,
      search: this.searchFilter
    };

    this.catalogService.getFilterProducts(filters).subscribe(
      (response: Product[]) => {
        this.products = response;
      },
      (error: HttpErrorResponse) => {
        console.log(error.message);
      }
    );

  }

  categoryChange(category, event): void {
    if (event.currentTarget.checked) {
      this.categoriesFilter.push(category);
    } else {
      this.categoriesFilter = this.categoriesFilter.filter(item => item !== category);
    }
  }

  orderChange(s: string): void {
    this.orderFilter = s;
    this.filterProducts();
  }

  searchChange(): void {
    this.filterProducts();
  }
}
