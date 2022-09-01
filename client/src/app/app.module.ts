import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import {HomeComponent} from './pages/home/home.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {LoginComponent} from './pages/login/login.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NavigationComponent } from './parts/navigation/navigation.component';
import { ProfileComponent } from './pages/profile/profile.component';
import {TokenInterceptor} from './auth/token.interceptor';
import { OrdersComponent } from './pages/orders/orders.component';
import { AddressComponent } from './pages/address/address.component';
import { OrderComponent } from './pages/order/order.component';
import { ArticleComponent } from './pages/article/article.component';
import { StudyhallsComponent} from "./pages/studyhalls/studyhalls.component";
// import { CartComponent } from './pages/cart/cart.component';
// import { CheckoutComponent } from './pages/checkout/checkout.component';
import { OrderSubmittedComponent } from './pages/order-submitted/order-submitted.component';
import { StoreComponent } from './pages/store/store.component';
import { AdminComponent } from './pages/admin/admin.component';
import { EditComponent } from './pages/admin/edit/edit.component';
import {DataTablesModule} from 'angular-datatables';
import {TagInputModule} from 'ngx-chips';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { AddComponent } from './pages/admin/add/add/add.component';
import { SocialLoginModule, SocialAuthServiceConfig } from 'angularx-social-login';
import { GoogleLoginProvider } from 'angularx-social-login'
import {IvyCarouselModule} from 'angular-responsive-carousel';
import {StudyhallsadminComponent} from "./pages/admin/studyhalls/studyhallsadmin.component";
import {StudyaddComponent} from "./pages/admin/studyhalls/add/studyadd.component";
import {StudyeditComponent} from "./pages/admin/studyhalls/edit/studyedit.component";

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    NavigationComponent,
    ProfileComponent,
    OrdersComponent,
    AddressComponent,
    OrderComponent,
    ArticleComponent,
    // CartComponent,
    // CheckoutComponent,
    OrderSubmittedComponent,
    StoreComponent,
    AdminComponent,
    EditComponent,
    AddComponent,
    StudyhallsComponent,
    StudyhallsadminComponent,
    StudyaddComponent,
    StudyeditComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
    DataTablesModule,
    TagInputModule,
    BrowserAnimationsModule,
    SocialLoginModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    },
    {
      provide: 'SocialAuthServiceConfig',
      useValue: {
        autoLogin: false,
        providers: [
          {
            id: GoogleLoginProvider.PROVIDER_ID,
            provider: new GoogleLoginProvider(
              '688776519545-moj3g4j8hlijm1d9cig1jc8trj8d7jf8.apps.googleusercontent.com'
            )
          }
        ]
      } as SocialAuthServiceConfig,
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
