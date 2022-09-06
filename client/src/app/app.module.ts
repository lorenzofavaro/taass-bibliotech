import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";

import { AppComponent } from "./app.component";
import { HomeComponent } from "./pages/home/home.component";
import { HTTP_INTERCEPTORS, HttpClientModule } from "@angular/common/http";
import { AppRoutingModule } from "./app-routing.module";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { LoginComponent } from "./pages/login/login.component";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { NavigationComponent } from "./parts/navigation/navigation.component";
import { ProfileComponent } from "./pages/profile/profile.component";
import { TokenInterceptor } from "./auth/token.interceptor";
import { OrdersComponent } from "./pages/orders/orders.component";
import { StudyHallsBookingsComponent } from "./pages/studyhalls_bookings/studyhalls_bookings.component";
import { OrderComponent } from "./pages/order/order.component";
import { ArticleComponent } from "./pages/article/article.component";
import { StudyhallsComponent } from "./pages/studyhalls/studyhalls.component";
import { StoreComponent } from "./pages/store/store.component";
import { AdminBooksComponent } from "./pages/admin/books/admin.component";
import { AdminBooksAddComponent } from "./pages/admin/books/add/add.component";
import { AdminBooksEditComponent } from "./pages/admin/books/edit/edit.component";
import { DataTablesModule } from "angular-datatables";
import { TagInputModule } from "ngx-chips";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import {
  SocialLoginModule,
  SocialAuthServiceConfig,
} from "angularx-social-login";
import { GoogleLoginProvider } from "angularx-social-login";
import { IvyCarouselModule } from "angular-responsive-carousel";
import { AdminStudyHallsComponent } from "./pages/admin/studyhalls/studyhallsadmin.component";
import { AdminStudyHallsAddComponent } from "./pages/admin/studyhalls/add/studyadd.component";
import { AdminStudyHallsEditComponent } from "./pages/admin/studyhalls/edit/studyedit.component";

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    NavigationComponent,
    ProfileComponent,
    OrdersComponent,
    StudyHallsBookingsComponent,
    OrderComponent,
    ArticleComponent,
    StoreComponent,
    AdminBooksComponent,
    AdminBooksEditComponent,
    AdminBooksAddComponent,
    StudyhallsComponent,
    AdminStudyHallsComponent,
    AdminStudyHallsAddComponent,
    AdminStudyHallsEditComponent,
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
    SocialLoginModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true,
    },
    {
      provide: "SocialAuthServiceConfig",
      useValue: {
        autoLogin: false,
        providers: [
          {
            id: GoogleLoginProvider.PROVIDER_ID,
            provider: new GoogleLoginProvider(
              "688776519545-moj3g4j8hlijm1d9cig1jc8trj8d7jf8.apps.googleusercontent.com"
            ),
          },
        ],
      } as SocialAuthServiceConfig,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
