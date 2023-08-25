import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
//const API_URL = '';
const API_URL = 'http://18.195.42.80:8888/api/v1/';
//const API_URL = 'http://127.0.0.1:8888/api/v1/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({
  providedIn: 'root'
})
export class ReportService {

 constructor(private http: HttpClient) { }

  getGroupedDataByCategory(userId: string, year: string, month: string): Observable<any> {

   console.log(userId, year, month)
      return this.http.post(API_URL + 'report', {
          userId,
          year,
          month
        }, httpOptions);
      }


}
