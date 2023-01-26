import { Component, OnInit } from '@angular/core';
import { RecordService } from '../_services/record.service';

@Component({
  selector: 'app-record',
  templateUrl: './record.component.html',
  styleUrls: ['./record.component.css']
})
export class RecordComponent implements OnInit{
 form: any = {
       createdAt: null,
       expenseTitleId: null,
       amount: null,
       comments: null,
       userId: null,
       currencyName: null,
       exchangeRateToRuble: null
  };
    isSuccessful = false;
    isSignUpFailed = false;
    errorMessage = '';

  constructor(private recordService: RecordService) { }

  ngOnInit(): void {
  }

  onSubmit(): void {
    const { createdAt, expenseTitleId, amount, comments, userId, currencyName, exchangeRateToRuble } = this.form;
    this.recordService.store(createdAt, expenseTitleId, amount, comments, userId, currencyName, exchangeRateToRuble).subscribe(
         data => {
           console.log(data);
           this.isSuccessful = true;
           this.isSignUpFailed = false;
         },
         err => {
           this.errorMessage = err.error.message;
           this.isSignUpFailed = true;
         }
    );
  }
}
