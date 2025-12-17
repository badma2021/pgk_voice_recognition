import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse} from '@angular/common/http';
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

}
