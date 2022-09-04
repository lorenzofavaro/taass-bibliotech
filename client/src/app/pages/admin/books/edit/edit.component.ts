/* tslint:disable:no-trailing-whitespace */
import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {CatalogService} from '../../../../services/catalog.service';
import {Product} from '../../../../models/product';
import {HttpErrorResponse} from '@angular/common/http';
import {noop} from 'rxjs';

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class AdminBooksEditComponent implements OnInit {

  constructor(private route: ActivatedRoute,
              private catalogService: CatalogService,
              private router: Router) { }

  productId: number;
  product: Product;
  public categories: string[];

  public error = false;

  public productCategories: string[] = [];

  ngOnInit(): void {
    this.productId = Number(this.route.snapshot.paramMap.get('id'));
    this.getArticle();
    this.getCategories();
  }

  private getArticle(): void {
    this.catalogService.getProduct(this.productId).subscribe(
      (response: Product) => {
        this.product = response;
        this.product.categories.forEach(category => {
          this.productCategories.push(category.name);
        });
      },
      (error: HttpErrorResponse) => {
        console.log(error.message);
      }
    );
  }

  editProduct(): void {
    console.log(this.product.title);
    const body = {
      id: this.product.id,
      title: this.product.title,
      description: this.product.description,
      picture: this.product.picture,
      author: this.product.author,
      stock: this.product.stock,
      categories: this.productCategories
    };
    this.catalogService.editProduct(body).subscribe(result => {
        this.router.navigate(['/admin']);
      },
      (error: HttpErrorResponse) => {
        this.error = true;
      });
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

  onCategoryRemove(tag): void {
    this.productCategories = this.productCategories.filter(item => item !== tag);
    console.log(this.product.categories);
  }

  addCategory(category: string): void {
    this.productCategories.indexOf(category) === -1 ? this.productCategories.push(category) : noop();
  }
}
