import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse, HttpRequest,HttpEvent } from '@angular/common/http';
import { SubCategory } from '../types/subCategory';
import { CreateExpense } from '../types/createExpense';
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

  create(expense: CreateExpense) {
    return this.http.post<CreateExpense>(
      API_URL + 'expenseTitle/create',
      expense,
      httpOptions
    );
  }

  update(category: SubCategory) {
    return this.http.put<SubCategory>(
      API_URL + 'expenseTitle/update',
      category,
      httpOptions
    );
  }

 deleteExpense(id: number) {
 const url = API_URL + 'expenseTitle/delete/' + id;
 return this.http.delete(url, httpOptions);
 }
}
