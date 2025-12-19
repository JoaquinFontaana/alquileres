import { TestBed } from '@angular/core/testing';
import { CanActivateChildFn } from '@angular/router';

import { clienteGuard } from './cliente-guard';

describe('clienteGuard', () => {
  const executeGuard: CanActivateChildFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => clienteGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
