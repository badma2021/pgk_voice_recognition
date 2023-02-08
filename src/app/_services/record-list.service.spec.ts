import { TestBed } from '@angular/core/testing';

import { RecordListService } from './record-list.service';

describe('RecordListService', () => {
  let service: RecordListService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RecordListService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
