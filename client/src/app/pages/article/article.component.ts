/* tslint:disable:no-trailing-whitespace */
import { AfterViewInit, Component, OnInit } from "@angular/core";
import { CatalogService } from "../../services/catalog.service";
import { ActivatedRoute, Router } from "@angular/router";
import { HttpErrorResponse } from "@angular/common/http";
import { Product } from "../../models/product";
import * as Feather from "feather-icons";
import { OrderService } from "../../services/order.service";

@Component({
  selector: "app-article",
  templateUrl: "./article.component.html",
  styleUrls: ["./article.component.css"],
})
export class ArticleComponent implements OnInit, AfterViewInit {
  productId: number;
  product: Product;

  errMsg = false;
  succMsg = false;
  already_ordered = false;
  user_unable = false;
  urlRedirect = "";

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private catalogService: CatalogService,
    private orderService: OrderService
  ) {}

  ngOnInit(): void {
    this.productId = Number(this.route.snapshot.paramMap.get("id"));
    this.getArticle();
    this.checkIfAlreadyOrdered();
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

  private checkIfAlreadyOrdered(): void {
    this.orderService.getOrderFromProductId(this.productId).subscribe(
      (response) => {
        if (response != null){
          this.already_ordered = true;
        }
      },
      (error: HttpErrorResponse) => {
        console.log(error.message);
      }
    );
  }

  public orderBook(): void {
    if (this.product.stock < 1) {
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
        products: productsMap2,
      };

      this.orderService.createOrder(newOrder).subscribe(
        (result) => {
          if (result == null) {
            this.succMsg = false;
            this.errMsg = true;
            this.user_unable = true;
          } else {
            this.errMsg = false;
            this.succMsg = true;
            this.urlRedirect = result.id;
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

  ngAfterViewInit(): void {
    Feather.replace();
  }
}
