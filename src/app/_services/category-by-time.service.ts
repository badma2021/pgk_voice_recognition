import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
//const API_URL = '';
const API_URL = 'http://localhost:8888/api/v1/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class CategoryByTimeService {

  constructor(private http: HttpClient) { }

    getCategoryByTime(userId: string, categoryId: string, expenseId: string): Observable<any> {

     //console.log(userId, year, month)
        return this.http.post(API_URL + 'categoryByTime', {
            userId,
            categoryId,
            expenseId
          }, httpOptions);
        }

//    getExpenseTitle(categoryId: number) {
//         return this.http.get(API_URL + 'selected_expensetitle?id=' + categoryId).pipe(
//           catchError(this.handleError)
//         );
//       }
//        private handleError(error: HttpErrorResponse) {
//           if (error.error instanceof ErrorEvent) {
//             // A client-side or network error occurred. Handle it accordingly.
//             console.error('An error occurred:', error.error.message);
//           } else {
//             // The backend returned an unsuccessful response code.
//             // The response body may contain clues as to what went wrong,
//             console.error(`Backend returned code ${error.status}, ` + `body was: ${error.error}`);
//           }
//           // return an observable with a user-facing error message
//           return throwError('Something bad happened. Please try again later.');
//         }
}
