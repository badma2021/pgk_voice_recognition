import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
//const API_URL = '';
const API_URL = 'http://18.195.42.80:8888/api/v1/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({
  providedIn: 'root'
})
export class HistoryService {

 constructor(private http: HttpClient) { }

  getHistory(userId: string, startDate: string, endDate: string, page: string, size: string): Observable<any> {

   console.log(userId, startDate, endDate)
      return this.http.post(API_URL + 'history', {
          userId,
          startDate,
          endDate,
          page,
          size
        }, httpOptions);
      }

 getLastFive(userId: string): Observable<any> {

   console.log(userId)
     return this.http.get(API_URL + 'history/lastFive?id=' + userId).pipe(
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

  delete(id:number){
  		return this.http.delete(API_URL + 'history/delete' + id)
  	}

   download(userId: string, startDate: string, endDate: string): Observable<HttpResponse<Blob>>{

       return this.http.post(API_URL + 'export', {userId,startDate,endDate}, {responseType: 'blob',observe: 'response'});
     }


}
