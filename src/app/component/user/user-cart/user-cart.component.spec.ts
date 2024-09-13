import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserCartComponent } from './user-cart.component';

describe('UserCartComponent', () => {
  let component: UserCartComponent;
  let fixture: ComponentFixture<UserCartComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserCartComponent]
    });
    fixture = TestBed.createComponent(UserCartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
