import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse, HttpRequest,HttpEvent } from '@angular/common/http';
import { Expense } from "../types/expense";
import { ExpenseTitle } from "../types/expenseTitle";
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

const AUTH_API = 'http://18.195.42.80:8888/api/v1/';
//const AUTH_API = 'http://127.0.0.1:8888/api/v1/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class RecordListService {

  constructor(private http: HttpClient) { }

  store(expenses): Observable<any> {
  console.log('hi from store')
   console.log(expenses)
  return this.http.post(AUTH_API + 'store', JSON.stringify(expenses), httpOptions);

  }

   getCategories(): Observable<any>{
      return this.http.get(AUTH_API + 'category').pipe(
                  catchError(this.handleError)
      );
    }

      getExpenseTitle(categoryId: number): Observable<any>{
        return this.http.get(AUTH_API + 'selected_expensetitle?id=' + categoryId).pipe(
          catchError(this.handleError)
        );
      }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong,
      console.error(`Backend returned code ${error.status}, ` + `body was: ${error.error}`);
    }
    // return an observable with a user-facing error message
    return throwError('Something bad happened. Please try again later.');
  }

   getFawaZahmedRates(cur: string): Observable<any>{
          return this.http.get(AUTH_API + 'rates/' + cur).pipe(
            catchError(this.handleError)
          );
        }


   upload(file: File, userId: string): Observable<any> {
      const formData: FormData = new FormData();
      formData.append('file', file);
      formData.append('userId', userId);
      return this.http.post(AUTH_API + 'upload-excel', formData);
    }


}
