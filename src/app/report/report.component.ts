import { Component, OnInit } from '@angular/core';
import { ReportService } from '../_services/report.service';
import { TokenStorageService } from '../_services/token-storage.service';
import {   ChartErrorEvent,
           ChartMouseLeaveEvent,
           ChartMouseOverEvent,
           ChartSelectionChangedEvent,
           ChartType,
           Column,
           GoogleChartComponent } from 'angular-google-charts';

const today = new Date();
const month0 = today.getMonth()+1;
const year0 = today.getFullYear()

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.css']
})
export class ReportComponent implements OnInit {

arr: any[] = [];
arr1: any[] = [["food", 0],
              ["travelling", 0],
              ["household", 0],
              ["travelling", 0]
                      ];

chartColumns=['category', 'value'];
barChart = ChartType.BarChart;

width=300;
height= 400;
columnsChartoptions={
labels: 'yes'};
content?: string;
  years = [
      '2021',
      '2022',
      '2023',
      '2024'
    ];
  year: string="";
    months = [
        '1',
        '2',
        '3',
        '4',
        '5',
        '6',
        '7',
        '8',
        '9',
        '10',
        '11',
        '12'
      ];
    month: string="";


  constructor(private reportService: ReportService, private tokenStorage: TokenStorageService) { }

  ngOnInit(): void {
   console.log('ngOnInit report starts');
this.year=year0.toString();
console.log('month');

this.month=month0.toString();

this.report();
  }

    onSearch2Change(searchValue2: string): void {
       console.log('searchValue2');
       console.log(searchValue2);
        this.year= searchValue2;
      }

     onSearchChange(searchValue1: string): void {
             console.log('searchValue1');
             console.log(searchValue1);
              this.month= searchValue1;
            }
    report() {
//console.log('report() starts');

          this.reportService.getGroupedDataByCategory(this.tokenStorage.getUser().userId,this.year, this.month).subscribe(
          data => {
        //   console.log('getGroupedDataByCategory starts');
          this.arr = [
          ];
          if (typeof data !== 'undefined' && data.length > 0){
          for(var i in data)
              this.arr.push([data[i].category, parseInt(data[i].value)]);

          console.log('this arr');
          console.log(this.arr);

           }
           else this.arr=this.arr1;
           }

     );
    }



}
