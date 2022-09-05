/* tslint:disable:no-trailing-whitespace */
import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { HomeComponent } from "./pages/home/home.component";
import { LoginComponent } from "./pages/login/login.component";
import { ProfileComponent } from "./pages/profile/profile.component";
import { AuthGuard } from "./auth/auth.guard";
import { OrdersComponent } from "./pages/orders/orders.component";
import { AddressComponent } from "./pages/address/address.component";
import { OrderComponent } from "./pages/order/order.component";
import { ArticleComponent } from "./pages/article/article.component";
import { StoreComponent } from "./pages/store/store.component";
import { AdminBooksComponent } from "./pages/admin/books/admin.component";
import { AdminBooksEditComponent } from "./pages/admin/books/edit/edit.component";
import { AdminBooksAddComponent } from "./pages/admin/books/add/add.component";
import { StudyhallsComponent } from "./pages/studyhalls/studyhalls.component";
import { AdminStudyHallsComponent } from "./pages/admin/studyhalls/studyhallsadmin.component";
import { AdminStudyHallsAddComponent } from "./pages/admin/studyhalls/add/studyadd.component";
import { AdminStudyHallsEditComponent } from "./pages/admin/studyhalls/edit/studyedit.component";

const routes: Routes = [
  { path: "", redirectTo: "/home", pathMatch: "full" },
  { path: "home", component: HomeComponent },
  { path: "login", component: LoginComponent },
  { path: "article/:id", component: ArticleComponent },
  { path: "store", component: StoreComponent },
  { path: "studyhalls", component: StudyhallsComponent },
  {
    path: "profile",
    component: ProfileComponent,
    canActivate: [AuthGuard],
  },
  {
    path: "orders",
    component: OrdersComponent,
    canActivate: [AuthGuard],
  },
  {
    path: "address",
    component: AddressComponent,
    canActivate: [AuthGuard],
  },
  {
    path: "order/:id",
    component: OrderComponent,
    canActivate: [AuthGuard],
  },
  {
    path: "admin/books",
    component: AdminBooksComponent,
    canActivate: [AuthGuard],
  },
  {
    path: "admin/books/add",
    component: AdminBooksAddComponent,
    canActivate: [AuthGuard],
  },
  {
    path: "admin/books/edit/:id",
    component: AdminBooksEditComponent,
    canActivate: [AuthGuard],
  },
  {
    path: "admin/studyhalls",
    component: AdminStudyHallsComponent,
    canActivate: [AuthGuard],
  },
  {
    path: "admin/studyhalls/add",
    component: AdminStudyHallsAddComponent,
    canActivate: [AuthGuard],
  },
  {
    path: "admin/studyhalls/edit/:id",
    component: AdminStudyHallsEditComponent,
    canActivate: [AuthGuard],
  },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
