/* tslint:disable:no-trailing-whitespace */
import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {BehaviorSubject, Observable, of} from 'rxjs';
import {environment} from '../../environments/environment';
import {JwtHelperService} from '@auth0/angular-jwt';
import {catchError, map, tap} from 'rxjs/operators';
import {UserAuth} from '../models/UserAuth';
import {User} from '../models/User';

@Injectable({
  providedIn: 'root'
})

export class UserService {
  private apiServerUrl = environment.apiBaseUrl;
  private currentUserSubject: BehaviorSubject<UserAuth>;
  public currentUser: Observable<UserAuth>;

  helper = new JwtHelperService();

  private user: UserAuth = {
    id: null,
    email: null,
    roles: null,
    token: null
  };

  constructor(private http: HttpClient) {
    const memo = localStorage.getItem('currentUser');
    this.currentUserSubject = new BehaviorSubject<UserAuth>(JSON.parse(memo));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  get currentUserValue(): UserAuth{
    return this.currentUserSubject.value;
  }

  public login(data): Observable<any> {
    return this.http.post(`${this.apiServerUrl}/auth-service/login`, data).pipe(
      tap(result => {
        if (result && result.jwt) {
          const decodedToken = this.helper.decodeToken(result.jwt);
          this.user.id = decodedToken.userId;
          this.user.email = decodedToken.sub;
          this.user.roles = decodedToken.authorities[0];
          this.user.token = result.jwt;

          localStorage.setItem('currentUser', JSON.stringify(this.user));
          this.currentUserSubject.next(this.user);
          return result;
        }
      })
    );
  }

  public loginWithGoogle(data): Observable<any> {
    return this.http.post(`${this.apiServerUrl}/auth-service/googlelogin`, data).pipe(
      tap(result => {
        if (result && result.jwt) {
          const decodedToken = this.helper.decodeToken(result.jwt);
          this.user.id = decodedToken.userId;
          this.user.email = decodedToken.sub;
          this.user.roles = decodedToken.authorities[0];
          this.user.token = result.jwt;

          localStorage.setItem('currentUser', JSON.stringify(this.user));
          this.currentUserSubject.next(this.user);
          return result;
        }
      })
    );
  }

  public signup(data): Observable<string> {
    return this.http.post(`${this.apiServerUrl}/auth-service/signup`, data, {responseType: 'text'});
  }

  public logout(): void {
    this.currentUserSubject.next(null);
    localStorage.removeItem('currentUser');
    localStorage.removeItem('cartItems');
  }

  public get(): Observable<User> {
    return this.http.get<User>(`${this.apiServerUrl}/user-service/user`);
  }

  public update(data): Observable<string> {
    return this.http.post(`${this.apiServerUrl}/user-service/user`, data, {responseType: 'text'});
  }

  updateAddress(data): Observable<string> {
    return this.http.post(`${this.apiServerUrl}/user-service/user/address`, data, {responseType: 'text'});
  }
}
