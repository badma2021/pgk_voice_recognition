import { Component, OnInit } from '@angular/core';
import { ReportService } from '../_services/report.service';
import { TokenStorageService } from '../_services/token-storage.service';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.css']
})
export class ReportComponent implements OnInit {
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
          console.log(data);
           }
     );
    }


}
