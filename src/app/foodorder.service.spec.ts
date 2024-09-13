import { TestBed } from '@angular/core/testing';

import { FoodorderService } from './foodorder.service';

describe('FoodorderService', () => {
  let service: FoodorderService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FoodorderService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
