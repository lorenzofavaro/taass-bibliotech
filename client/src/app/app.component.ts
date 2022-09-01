/* tslint:disable:no-trailing-whitespace */
import {AfterViewInit, Component, NgZone} from '@angular/core';
import {NavigationEnd, Router} from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'client';
  showHeader: boolean;

  constructor(private zone: NgZone, private router: Router) {
    this.router.events.subscribe((event: any) => {
      if (event instanceof NavigationEnd) {
        if (event.url === '/login') {
          this.showHeader = true;
        } else {
          this.showHeader = true;
        }
      }
    });
  }
}
