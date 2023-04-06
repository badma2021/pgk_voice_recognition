import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoryByTimeComponent } from './category-by-time.component';

describe('CategoryByTimeComponent', () => {
  let component: CategoryByTimeComponent;
  let fixture: ComponentFixture<CategoryByTimeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CategoryByTimeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CategoryByTimeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });
  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
