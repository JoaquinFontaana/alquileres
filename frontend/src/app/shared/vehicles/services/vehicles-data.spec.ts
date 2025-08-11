import { TestBed } from '@angular/core/testing';

import { VehiclesData } from './vehicles-data';

describe('VehiclesData', () => {
  let service: VehiclesData;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VehiclesData);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
