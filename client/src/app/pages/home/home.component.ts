import {AfterViewInit, Component, OnInit} from '@angular/core';
import {CatalogService} from '../../services/catalog.service';
import {Product} from '../../models/product';
import {StudyhallsService} from "../../services/studyhalls.service";
import {Studyhalls} from "../../models/Studyhalls";
import {HttpErrorResponse} from '@angular/common/http';
import * as Feather from 'feather-icons';


@Component({
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})

export class HomeComponent implements OnInit, AfterViewInit {
  public products: Product[];
  public studyhalls: Studyhalls[] = [];

  slideConfig = {
    "slidesToShow": 4,
    "slidesToScroll": 4,
    "nextArrow": "<div class='nav-btn next-slide'></div>",
    "prevArrow": "<div class='nav-btn prev-slide'></div>",
    "dots": false,
    "infinite": false
  };

  constructor(
    private catalogService: CatalogService,
    private studyhallsService: StudyhallsService) {}

  ngOnInit(): void {
    this.getMostAvailableStudyHalls();
    this.getFeaturedProducts();
  }

  public getFeaturedProducts(): void {
    this.catalogService.getFeatured().subscribe(
      (response: Product[]) => {
        this.products = response;
        console.log(this.products)
      },
      (error: HttpErrorResponse) => {
       console.log(error.message);
      }
    );
  }

  public getMostAvailableStudyHalls(): void {
      let max_count = 5
      this.studyhallsService.getMostAvailableStudyHalls(max_count).subscribe(
        (response: Studyhalls[]) => {
          this.studyhalls = response;
        },
        (error: HttpErrorResponse) => {
          console.log(error.message);
        }
      );
    }

  ngAfterViewInit(): void {
    Feather.replace();
  }
}
