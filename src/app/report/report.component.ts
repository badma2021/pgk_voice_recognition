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

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.css']
})
export class ReportComponent implements OnInit {

arr: any[] = [];
// arr: any[] = [["rental(рентл)", 30000],
//               ["travelling", 10000],
//               ["household", 6000],
//               ["food", 1000],
//               ["rental(рентл)", 30000],
//                             ["travelling", 10000],
//                             ["household", 6000]];

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

          this.reportService.getGroupedDataByCategory(this.tokenStorage.getUser().userId,this.year, this.month).subscribe(
          data => {
          this.arr = [];
          for(var i in data)
              this.arr.push([data[i].category, data[i].value]);

          //this.chartData.data = Object.keys(data).map((key) => [key, data[key]]);
          console.log('this arr');
          console.log(this.arr);
         //  console.log('data');
        //  console.log(data);
           }
     );
    }



}
