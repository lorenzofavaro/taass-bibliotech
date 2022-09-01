/* tslint:disable:no-trailing-whitespace */
import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {CatalogService} from '../../../../services/catalog.service';
import {Product} from '../../../../models/product';
import {HttpErrorResponse} from '@angular/common/http';
import {noop} from 'rxjs';

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css']
})
export class AddComponent implements OnInit {

  constructor(private route: ActivatedRoute,
              private catalogService: CatalogService,
              private router: Router) { }

  productId: number;
  public product: Product = {categories: [], description: '', id: 0, picture: '', author: '', stock: 0, title: ''};
  public categories: string[];

  public error = false;

  public productCategories: string[] = [];

  ngOnInit(): void {
    this.getCategories();
  }

  addProduct(): void {
    const body = {
      title: this.product.title,
      description: this.product.description,
      picture: this.product.picture,
      author: this.product.author,
      stock: this.product.stock,
      categories: this.productCategories
    };
    this.catalogService.addProduct(body).subscribe(result => {
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
