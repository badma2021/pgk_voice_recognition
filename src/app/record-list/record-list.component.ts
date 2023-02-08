import { Component } from '@angular/core';
import { FormGroup, FormBuilder, FormArray } from '@angular/forms';
import { RecordListService } from '../_services/record-list.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { Expense } from "../types/expense";

@Component({
  selector: 'app-record-list',
  templateUrl: './record-list.component.html',
  styleUrls: [ './record-list.component.css' ]
})
export class RecordListComponent {
  filterTypes = [
    'food',
    'travelling',
    'ffr'
  ];

  expenseTitleIdes = [
    '1',
    '2',
    '3'
  ];
   currencyNames = [
      'RUB',
      'RSD',
      'EUR'
    ];
    amount: number=0;
    comment: string="";
    exchangeRateToRuble: string="";

  seedData = [
    { filterType: 'TRANSFER TM', expenseTitleId: 'Less Than', amount: '100' },
    { filterType: 'TRANSFER TM' },
    { filterType: 'TRANSFER TM', expenseTitleId: 'Equals', amount: '50' },
    { filterType: 'TRANSFER TM', expenseTitleId: 'Equals' },
    { filterType: 'TRANSFER TM', expenseTitleId: 'Greater Than', amount: '150' },
    { filterType: 'APP', expenseTitleId: 'Less Than', amount: '100' },
    { filterType: 'APP', expenseTitleId: 'Equals', amount: '50' },
    { filterType: 'APP' },
    { filterType: 'APP', expenseTitleId: 'Greater Than' },
  ];

  dynamicForm: FormGroup;

  constructor(private fb: FormBuilder, private recordListService: RecordListService, private tokenStorage: TokenStorageService) {}

  ngOnInit() {
    this.dynamicForm = this.fb.group({
      filters: this.fb.array([])
    });

    // Uncomment the line below If you want to seed the Form with some data
   // this.seedFiltersFormArray();
  }

  seedFiltersFormArray() {
    this.seedData.forEach(seedDatum => {
      const formGroup = this.createFilterGroup();
      if (seedDatum.expenseTitleId) {
        formGroup.addControl('amount', this.getFormControl());
      }
      formGroup.patchValue(seedDatum);
      this.filtersFormArray.push(formGroup);
    });
  }

  createFilterGroup() {
    return this.fb.group({
    createdAt: '',
      expenseTitleId: [],
      currencyName: [],
      amount: '',
      comment: '',
      exchangeRateToRuble: '',
      userId: this.tokenStorage.getUser().userId
    });
  }

  addFilterToFiltersFormArray() {
    this.filtersFormArray.push(this.createFilterGroup());
  }

  removeFilterFromFiltersFormArray(index) {
    this.filtersFormArray.removeAt(index);
  }

  selectedAPIChanged(i) {
    this.getFilterGroupAtIndex(i).addControl('amount', this.getFormControl());
  }

  getFormControl() {
    return this.fb.control(null);
  }

  save() {



const control = this.dynamicForm.get('filters').value;


let expenses: Expense[] = control;
console.log(expenses);
        this.recordListService.store(control).subscribe(
                                               data => {
                                                 console.log(data);

                                               }
);

  }

  get filtersFormArray() {
    return (<FormArray>this.dynamicForm.get('filters'));
  }

  getFilterGroupAtIndex(index) {
    return (<FormGroup>this.filtersFormArray.at(index));
  }
}
