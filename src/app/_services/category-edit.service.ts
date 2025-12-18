import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse, HttpRequest,HttpEvent } from '@angular/common/http';
import { Category } from '../types/category';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from '../../environments/environment';

const API_URL = environment.apiUrl;
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({ providedIn: 'root' })
export class CategoryEditService {

  constructor(private http: HttpClient) {}

getAll(userId: number) {
  return this.http.get<Category[]>(
    API_URL + 'category/' + userId
  );
}

create(category: Category) {
  return this.http.post<Category>(
    API_URL + 'category/create',
    category,
    httpOptions
  );
}

update(category: Category) {
  return this.http.put<Category>(
    API_URL + 'category/update',
    category,
    httpOptions
  );
}
}
