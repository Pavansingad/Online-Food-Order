import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserOrderHistoryComponent } from './user-order-history.component';

describe('UserOrderHistoryComponent', () => {
  let component: UserOrderHistoryComponent;
  let fixture: ComponentFixture<UserOrderHistoryComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserOrderHistoryComponent]
    });
    fixture = TestBed.createComponent(UserOrderHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
