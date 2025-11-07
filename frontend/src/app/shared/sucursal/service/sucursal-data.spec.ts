import { TestBed } from '@angular/core/testing';

import { SucursalData } from './sucursal-data';

describe('SucursalData', () => {
  let service: SucursalData;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SucursalData);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
