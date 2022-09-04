/* tslint:disable:no-trailing-whitespace */
import {AfterViewInit, Component, OnInit} from '@angular/core';
import {CatalogService} from '../../services/catalog.service';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpErrorResponse} from '@angular/common/http';
import {Product} from '../../models/product';
import * as Feather from 'feather-icons';
import {OrderService} from "../../services/order.service";
import {Order} from "../../models/order";


@Component({
  selector: 'app-article',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.css']
})
export class ArticleComponent implements OnInit, AfterViewInit {

  productId: number;
  product: Product;

  errMsg = false;
  succMsg = false;
  urlRedirect = '';

  constructor(private route: ActivatedRoute,
              private router: Router,
              private catalogService: CatalogService,
              private orderService: OrderService) { }

  ngOnInit(): void {
    this.productId = Number(this.route.snapshot.paramMap.get('id'));
    this.getArticle();
  }

  private getArticle(): void {
    this.catalogService.getProduct(this.productId).subscribe(
      (response: Product) => {
        this.product = response;
      },
      (error: HttpErrorResponse) => {
        console.log(error.message);
      }
    );
  }

  public bookingBook(): void {
    if (1 > this.product.stock) {
      this.succMsg = false;
      this.errMsg = true;
    } else {

    const productsMap: Map<number, number> = new Map<number, number>();
    productsMap.set(this.productId, 1);
    const productsMap2 = {};
    productsMap.forEach((val: number, key: number) => {
        productsMap2[key] = val;
    });

    const newOrder = {
      products: productsMap2
    }

    this.orderService.createOrder(newOrder).subscribe(result => {
        this.errMsg = false;
        this.succMsg = true;
        this.urlRedirect = result.id
      },
      (error: HttpErrorResponse) => {
        this.succMsg = false;
        this.errMsg = true;
        if (error.status == 401){ // Unauthorized
          this.router.navigate(['/login']);
        }
      }
    )
    }
  }

  ngAfterViewInit(): void {
    Feather.replace();
  }
}
