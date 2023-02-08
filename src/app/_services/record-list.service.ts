import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Expense } from "../types/expense";
import { Observable } from 'rxjs';

const AUTH_API = 'http://localhost:8888/api/v1/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class RecordListService {
 expenses: Expense[] = [
      {
        "createdAt": "",
        "expenseTitleId": "4",
        "amount": "899",
        "comment": "electronic",
        "userId": "1",
        "currencyName": "RSD",
        "exchangeRateToRuble": "0.64"
      },
      {
        "createdAt": "",
        "expenseTitleId": "3",
        "amount": "799",
        "comment": "electronic",
        "userId": "1",
        "currencyName": "RSD",
        "exchangeRateToRuble": "0.64"
      }

   ]
  constructor(private http: HttpClient) { }

  store(expenses): Observable<any> {
  console.log('hi from store')
   console.log(expenses)
  return this.http.post(AUTH_API + 'store', JSON.stringify(expenses), httpOptions);

  }
}
