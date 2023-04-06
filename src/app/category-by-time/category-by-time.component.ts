import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormArray } from '@angular/forms';
import { CategoryByTimeService } from '../_services/category-by-time.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { RecordListService } from '../_services/record-list.service';
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
  selector: 'app-category-by-time',
  templateUrl: './category-by-time.component.html',
  styleUrls: ['./category-by-time.component.css']
})
export class CategoryByTimeComponent implements OnInit {
filterTypes ;
expenseTitleIds = [];
// filterType={
// id: '',
// categoryName: ''
// };
categoryId : string='0';
expenseId : string='0';

//    arr = [
//       ["Jan",  7.0, -0.2, -0.9, 3.9],
//       ["Feb",  6.9, 0.8, 0.6, 4.2],
//       ["Mar",  9.5,  5.7, 3.5, 5.7],
//       ["Apr",  14.5, 11.3, 8.4, 8.5],
//       ["May",  18.2, 17.0, 13.5, 11.9],
//       ["Jun",  21.5, 22.0, 17.0, 15.2],
//       ["Jul",  25.2, 24.8, 18.6, 17.0],
//       ["Aug",  26.5, 24.1, 17.9, 16.6],
//       ["Sep",  23.3, 20.1, 14.3, 14.2],
//       ["Oct",  18.3, 14.1, 9.0, 10.3],
//       ["Nov",  13.9,  8.6, 3.9, 6.6],
//       ["Dec",  9.6,  2.5,  null, null]
//    ];
     arr = [];
//   chartColumns = ["Month", "Tokyo", "New York","Berlin", "Paris"];
    chartColumns = [];
   arr1 = [null
   ];
   chartColumns1 = [null];
//       arr = [
//          ["2021",  7.0, -0.2, -0.9, 3.9],
//          ["2022",  6.9, 0.8, 0.6, 4.2],
//          ["2023",  9.5,  5.7, 3.5, 5.7]
//
//       ];
//       chartColumns = ["Year", "Jan", "Feb","March", "April"];
   options = {
      hAxis: {
         title: 'Month',
      },
      vAxis:{
         title: 'Temperature',
},

   };
   width = 350;
   height = 400;
lineChart = ChartType.LineChart;


columnsChartoptions={
labels: 'yes',
curveType: 'function',
   legend: { position: 'bottom' },
   fontSize:10,
   crosshair:{
   	     color:'#000000',
   	     trigger:'selection'
         }
           };
content?: string;



  constructor(private categoryByTimeService: CategoryByTimeService, private tokenStorage: TokenStorageService, private recordListService: RecordListService) { }

  ngOnInit(): void {
  this.recordListService.getCategories().subscribe(

        data => this.filterTypes = data
      // console.log(data)
      );
   // this.dynamicForm = this.fb.group({
     // filters: this.fb.array([])
   // });

this.categoryId='2';
this.categoryByTime();

  }
  onChangeCategory(categoryId: number) {
   console.log("hi from onChangeCategory");

      if (categoryId) {
        this.recordListService.getExpenseTitle(categoryId).subscribe(
          data =>
            this.expenseTitleIds = data

        );
      } else {
      console.log("hi from onChangeCategory else");
        this.expenseTitleIds = null;

      }
        this.expenseId='0';
    }
 categoryByTime() {
 console.log('categoryByTime() starts');

           this.categoryByTimeService.getCategoryByTime(this.tokenStorage.getUser().userId,this.categoryId, this.expenseId).subscribe(
           data => {
         //   console.log('getGroupedDataByCategory starts');
           this.arr = [
           ];
          // if (typeof data !== 'undefined' && data.length > 0){

                this.arr = data['arr'];
                this.chartColumns = data['chartColumns'];

           console.log('this arr');
           console.log(this.arr);
console.log('this chartColumns');
           console.log(this.chartColumns);

            }
//             else
//             this.arr=this.arr1;
//             this.chartColumns=this.chartColumns1;
//             }

      );

     }

}
