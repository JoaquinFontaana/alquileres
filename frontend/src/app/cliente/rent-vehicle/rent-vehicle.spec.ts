import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RentVehicle } from './rent-vehicle';

describe('RentVehicle', () => {
  let component: RentVehicle;
  let fixture: ComponentFixture<RentVehicle>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RentVehicle]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RentVehicle);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
