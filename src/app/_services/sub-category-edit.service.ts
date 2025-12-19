import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse, HttpRequest,HttpEvent } from '@angular/common/http';
import { SubCategory } from '../types/subCategory';

import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from '../../environments/environment';

const API_URL = environment.apiUrl;
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class SubCategoryEditService {

    constructor(private http: HttpClient) {}

  getAll(userId: number) {
    return this.http.get<SubCategory[]>(
      API_URL + 'category/' + userId
    );
  }

  create(category: SubCategory) {
    return this.http.post<SubCategory>(
      API_URL + 'category/create',
      category,
      httpOptions
    );
  }

  update(category: SubCategory) {
    return this.http.put<SubCategory>(
      API_URL + 'category/update',
      category,
      httpOptions
    );
  }
}
