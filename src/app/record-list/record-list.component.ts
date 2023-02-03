import { Component } from '@angular/core';
import { FormGroup, FormBuilder, FormArray } from '@angular/forms';

@Component({
  selector: 'app-record-list',
  templateUrl: './record-list.component.html',
  styleUrls: [ './record-list.component.css' ]
})
export class RecordListComponent {
  filterTypes = [
    'TRANSFER TM',
    'APP'
  ];

  apiTypes = [
    'Less Than',
    'Equals',
    'Greater Than'
  ];

  seedData = [
    { filterType: 'TRANSFER TM', apiType: 'Less Than', value: '100' },
    { filterType: 'TRANSFER TM' },
    { filterType: 'TRANSFER TM', apiType: 'Equals', value: '50' },
    { filterType: 'TRANSFER TM', apiType: 'Equals' },
    { filterType: 'TRANSFER TM', apiType: 'Greater Than', value: '150' },
    { filterType: 'APP', apiType: 'Less Than', value: '100' },
    { filterType: 'APP', apiType: 'Equals', value: '50' },
    { filterType: 'APP' },
    { filterType: 'APP', apiType: 'Greater Than' },
  ];

  dynamicForm: FormGroup;

  constructor(private fb: FormBuilder) {}

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
      if (seedDatum.apiType) {
        formGroup.addControl('value', this.getFormControl());
        formGroup.addControl('comment', this.getFormControl());
      }
      formGroup.patchValue(seedDatum);
      this.filtersFormArray.push(formGroup);
    });
  }

  createFilterGroup() {
    return this.fb.group({
      filterType: [],
      apiType: []
    });
  }

  addFilterToFiltersFormArray() {
    this.filtersFormArray.push(this.createFilterGroup());
  }

  removeFilterFromFiltersFormArray(index) {
    this.filtersFormArray.removeAt(index);
  }

  selectedAPIChanged(i) {
    this.getFilterGroupAtIndex(i).addControl('value', this.getFormControl());
  }

  getFormControl() {
    return this.fb.control(null);
  }

  save() {
    console.log(this.dynamicForm.value);
  }

  get filtersFormArray() {
    return (<FormArray>this.dynamicForm.get('filters'));
  }

  getFilterGroupAtIndex(index) {
    return (<FormGroup>this.filtersFormArray.at(index));
  }
}
