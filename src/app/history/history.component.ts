import { Component, Inject, OnInit } from '@angular/core';
import {FormGroup, FormControl} from '@angular/forms';
import { TokenStorageService } from '../_services/token-storage.service';
import { HistoryService } from '../_services/history.service';
import { PageEvent } from '@angular/material/paginator';
import { DatePipe } from '@angular/common';

export interface PeriodicElement {
  date: string;
  name: number;
  value: string;
}

// const ELEMENT_DATA: PeriodicElement[] = [
// {date: 'Hydrogen', name: 1.0079, value: 'H'},
// {date: 'Helium', name: 4.0026, value: 'He'},
// {date: 'Lithium', name: 6.941, value: 'Li'},
// {date: 'Beryllium', name: 9.0122, value: 'Be'},
// {date: 'Boron', name: 10.811, value: 'B'},
// {date: 'Carbon', name: 12.0107, value: 'C'},
// {date: 'Nitrogen', name: 14.0067, value: 'N'},
// {date: 'Oxygen', name: 15.9994, value: 'O'},
// {date: 'Fluorine', name: 18.9984, value: 'F'},
// {date: 'Neon', name: 20.1797, value: 'Ne'},
// ];

const today = new Date();
const month = today.getMonth();
const year = today.getFullYear()

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']

})
export class HistoryComponent implements OnInit {
 start: string="";
  end: string="";
dataSource: any[] = [];
  displayedColumns: string[] = ['date', 'expenseName', 'value', 'edit'];
//dataSource = ELEMENT_DATA;
   campaignOne = new FormGroup({
      start: new FormControl(new Date(year, month, 13)),
      end: new FormControl(new Date(year, month, 16)),
    });

    page: string='0';
    size: string='5';
    totalElements: number = 0;

  constructor(private historyService: HistoryService, private tokenStorage: TokenStorageService,private datePipe: DatePipe) { }

  ngOnInit(): void {
this.historyService.getLastFive(this.tokenStorage.getUser().userId).subscribe(
          data => {
           console.log('data');
         console.log(data);
         this.dataSource = [];
         this.dataSource = data;
         this.totalElements = data['totalElements'];
         console.log('totalElements');
         console.log(this.totalElements );
//          for(var i in data)
//              this.dataSource.push([data[i].date, data[i].expenseName, data[i].value]);
          console.log('this dataSource');
          console.log(this.dataSource);
           }
           );
  }

    showHistory() {
    this.start=this.datePipe.transform(this.start,"yyyy-MM-dd");
    this.end=this.datePipe.transform(this.end,"yyyy-MM-dd");
 console.log('startdate');
  console.log(this.start);
 console.log('enddate');
  console.log(this.end);

          this.historyService.getHistory(this.tokenStorage.getUser().userId,this.start, this.end, this.page, this.size).subscribe(
          data => {
           console.log('data');
                    console.log(data);
         this.dataSource = [];
      this.dataSource = data['content'];
             this.totalElements = data['totalElements'];
              console.log('totalElements');
                                 console.log(this.totalElements );
//          for(var i in data)
//              this.dataSource.push([data[i].date, data[i].expenseName, data[i].value]);
          console.log('this dataSource');
          console.log(this.dataSource);
           }
     );
};

	nextPage(event: PageEvent) {
		const request = {};
		this.page = event.pageIndex.toString();
		this.size = event.pageSize.toString();
		this.showHistory();
	}

		deleteRecord(id:number){
		if(window.confirm('Are sure you want to delete this item?')){
  		this.historyService.delete(id)
  		.subscribe(data => {
  			//this.products = this.products.filter(item => item.id !== id);
  			console.log('Record deleted successfully!');
  		}
  		, error => {
  			console.log(error.error.message);
  		}
  		);
  		}
  	}

}
