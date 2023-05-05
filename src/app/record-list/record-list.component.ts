import { Component } from '@angular/core';
import { FormGroup, FormBuilder, FormArray } from '@angular/forms';
import { RecordListService } from '../_services/record-list.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { Expense } from "../types/expense";
import { Router } from '@angular/router';


@Component({
  selector: 'app-record-list',
  templateUrl: './record-list.component.html',
  styleUrls: [ './record-list.component.css' ]
})
export class RecordListComponent {
filterTypes ;
  expenseTitleIds= [];
  currencyNames = [
      'rub',
      'rsd',
      'eur',
      'usd',
      'try',
      'kzt',
      'uzs'
    ];
  currencyName: string="";

    amount: number=0;
    comment: string="";
    exchangeRateToRuble: string="";


  dynamicForm: FormGroup;


  constructor(private fb: FormBuilder, private recordListService: RecordListService, private tokenStorage: TokenStorageService, private router: Router) {}

  ngOnInit() {

  this.recordListService.getCategories().subscribe(

        data => this.filterTypes = data
       // console.log(data);
      );
    this.dynamicForm = this.fb.group({
      filters: this.fb.array([])
    });

  }


  createFilterGroup() {
    return this.fb.group({
    createdAt: '',
      expenseTitleId: [],
      currencyName: this.currencyName,
      amount: '',
      comment: '',
      exchangeRateToRuble: this.exchangeRateToRuble,
      userId: this.tokenStorage.getUser().userId
    });
  }

  addFilterToFiltersFormArray() {
    this.filtersFormArray.push(this.createFilterGroup());
  }

  removeFilterFromFiltersFormArray(index) {
    this.filtersFormArray.removeAt(index);
  }


  getFormControl() {
    return this.fb.control(null);
  }

  save() {

const control = this.dynamicForm.get('filters').value;
let expenses: Expense[] = control;
console.log("expenses");
console.log(expenses);

expenses.forEach(Expense=> Expense.currencyName = this.currencyName);
expenses.forEach(Expense=> Expense.exchangeRateToRuble = this.exchangeRateToRuble);
console.log(this.currencyName);
console.log(this.exchangeRateToRuble);
        this.recordListService.store(expenses).subscribe(
        data => {
        console.log(data);
         }
   );
   this.dynamicForm.reset();
  }

  get filtersFormArray() {
    return (<FormArray>this.dynamicForm.get('filters'));
  }

  getFilterGroupAtIndex(index) {
    return (<FormGroup>this.filtersFormArray.at(index));
  }

   onChangeCategory(categoryId: number, index: number) {
   console.log("hi from onChangeCategory");
      if (categoryId) {
        this.recordListService.getExpenseTitle(categoryId).subscribe(
          data => {
            this.expenseTitleIds[index] = data;
  console.log(data);
          }
        );
      } else {
      console.log("hi from onChangeCategory else");
        this.expenseTitleIds[index] = null;

      }
    }
  selectedAPIChanged(i) {
    this.getFilterGroupAtIndex(i).addControl('amount', this.getFormControl());

  }
  onSearchChange(searchValue: string): void {
   console.log('searchValue');
    this.exchangeRateToRuble= searchValue;
  }
   onSearch2Change(searchValue2: string): void {
     console.log('searchValue2');
     console.log(searchValue2);
      this.currencyName= searchValue2;

      this.recordListService.getFawaZahmedRates(searchValue2).subscribe(
              data => {
              console.log(data);
              this.exchangeRateToRuble=data['rub'];
               }
               );

    }


}
