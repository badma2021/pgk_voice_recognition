import { TestBed } from '@angular/core/testing';

import { CategoryByTimeService } from './category-by-time.service';

describe('CategoryByTimeService', () => {
  let service: CategoryByTimeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CategoryByTimeService);
  });
  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
