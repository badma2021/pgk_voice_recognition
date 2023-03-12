import { Component, Inject } from '@angular/core';
import {FormGroup, FormControl} from '@angular/forms';



export interface PeriodicElement {
  date: string;
  name: number;
  value: string;
}

const ELEMENT_DATA: PeriodicElement[] = [
{date: 'Hydrogen', name: 1.0079, value: 'H'},
{date: 'Helium', name: 4.0026, value: 'He'},
{date: 'Lithium', name: 6.941, value: 'Li'},
{date: 'Beryllium', name: 9.0122, value: 'Be'},
{date: 'Boron', name: 10.811, value: 'B'},
{date: 'Carbon', name: 12.0107, value: 'C'},
{date: 'Nitrogen', name: 14.0067, value: 'N'},
{date: 'Oxygen', name: 15.9994, value: 'O'},
{date: 'Fluorine', name: 18.9984, value: 'F'},
{date: 'Neon', name: 20.1797, value: 'Ne'},
];

const today = new Date();
const month = today.getMonth();
const year = today.getFullYear()

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']

})
export class HistoryComponent {

  displayedColumns: string[] = ['date', 'name', 'value'];
  dataSource = ELEMENT_DATA;
   campaignOne = new FormGroup({
      start: new FormControl(new Date(year, month, 13)),
      end: new FormControl(new Date(year, month, 16)),
    });
    campaignTwo = new FormGroup({
      start: new FormControl(new Date(year, month, 15)),
      end: new FormControl(new Date(year, month, 19)),
    });


}
