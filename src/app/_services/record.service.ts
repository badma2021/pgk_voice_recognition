import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

const AUTH_API = 'http://localhost:8888/api/v1/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class RecordService {

  constructor(private http: HttpClient) { }

  store(createdAt: string, expenseTitleId: string, amount: string, comments: string, userId: string, currencyName: string, exchangeRateToRuble: string): Observable<any> {
  console.log('hi from store')
    return this.http.post(AUTH_API + 'store', {
       createdAt,
       expenseTitleId,
       amount,
       comments,
       userId,
       currencyName,
       exchangeRateToRuble
    }, httpOptions);
  }
}
