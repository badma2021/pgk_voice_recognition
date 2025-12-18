import { TestBed } from '@angular/core/testing';

import { SubCategoryEditService } from './sub-category-edit.service';

describe('SubCategoryEditService', () => {
  let service: SubCategoryEditService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SubCategoryEditService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
