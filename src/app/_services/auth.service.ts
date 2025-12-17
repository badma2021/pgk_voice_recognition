import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment } from '../../environments/environment';

const API_URL = environment.apiUrl;
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) { }

  login(email: string, password: string): Observable<any> {
  console.log(email)
  console.log(password)
    return this.http.post(API_URL + 'login', {
      email,
      password
    }, httpOptions);
  }

  register(firstName: string, lastName: string,  email: string, password: string): Observable<any> {
    return this.http.post(API_URL + 'register', {
      firstName,
      lastName,
      email,
      password
    }, httpOptions);
  }

  refreshToken(token: string) {
    return this.http.post(API_URL + 'refreshtoken', {
      refreshToken: token
    }, httpOptions);
}
}
