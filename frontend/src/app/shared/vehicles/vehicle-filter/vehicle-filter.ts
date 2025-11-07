import { Component, inject, signal } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, AbstractControl, ValidationErrors } from '@angular/forms';
import { Button } from '@shared/button/button';
import { VehiclesStore } from '@vehicles/vehicles-store';
import {SucursalStore} from '@shared/sucursal/sucursal-store'
import { VehicleFilter as VehicleFilterModel } from '@models';

@Component({
  selector: 'app-vehicle-filter',
  imports: [ReactiveFormsModule, Button],
  templateUrl: './vehicle-filter.html',
  styleUrl: './vehicle-filter.scss'
})
export class VehicleFilter {
  private readonly fb = inject(FormBuilder);
  readonly vehicleStore = inject(VehiclesStore);
  readonly sucursalStore = inject(SucursalStore);
  // Fecha mínima (hoy)
  readonly today = new Date().toISOString().split('T')[0];
  
  // Mensaje de error
  readonly errorMessage = signal<string | null>(null);

  public form = this.fb.group({
    nombreSucursal: [''],
    categorias: [''],
    fechaDesde: [''],
    fechaHasta: ['']
  }, { validators: [this.dateRangeValidator()] });


  // Validador personalizado para el rango de fechas
  private dateRangeValidator() {
    return (control: AbstractControl): ValidationErrors | null => {
      const fechaDesde = control.get('fechaDesde')?.value;
      const fechaHasta = control.get('fechaHasta')?.value;

      // Si solo una fecha está presente, ambas son requeridas
      if ((fechaDesde && !fechaHasta) || (!fechaDesde && fechaHasta)) {
        return { ambasFechasRequeridas: true };
      }

      // Si ambas están vacías, es válido
      if (!fechaDesde && !fechaHasta) {
        return null;
      }

      const desde = new Date(fechaDesde);
      const hasta = new Date(fechaHasta);
      const hoy = new Date();
      hoy.setHours(0, 0, 0, 0);

      // Validar que fecha desde sea hoy o después
      if (desde < hoy) {
        return { fechaPasada: true };
      }

      // Validar que fecha hasta sea después de fecha desde
      if (hasta < desde) {
        return { fechaInvalida: true };
      }

      // Validar que el rango no sea mayor a 14 días
      const diffTime = hasta.getTime() - desde.getTime();
      const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));

      if (diffDays > 14) {
        return { rangoExcedido: true };
      }

      return null;
    };
  }

  applyFilters(): void {
    this.errorMessage.set(null);

    if (this.form.invalid) {
      const errors = this.form.errors;
      
      if (errors?.['ambasFechasRequeridas']) {
        this.errorMessage.set('Si seleccionas una fecha, debes completar ambas');
      } else if (errors?.['fechaPasada']) {
        this.errorMessage.set('La fecha de inicio debe ser hoy o posterior');
      } else if (errors?.['fechaInvalida']) {
        this.errorMessage.set('La fecha hasta debe ser posterior a la fecha desde');
      } else if (errors?.['rangoExcedido']) {
        this.errorMessage.set('El rango máximo permitido es de 14 días');
      } else {
        this.errorMessage.set('Por favor, verifica los campos del formulario');
      }
      
      return;
    }

    const formValue = this.form.value;
    const filter = new VehicleFilterModel();
    
    if (formValue.nombreSucursal) {
      filter.setNombreSucursal(formValue.nombreSucursal);
    }
    
    if (formValue.categorias) {
      filter.setCategoria(formValue.categorias);
    }
    
    if (formValue.fechaDesde) {
      filter.setFechaDesde(formValue.fechaDesde);
    }
    
    if (formValue.fechaHasta) {
      filter.setFechaHasta(formValue.fechaHasta);
    }
    
    this.vehicleStore.loadVehicles(filter);
  }

  clearFilters(): void {
    this.form.reset({
      nombreSucursal: '',
      categorias: '',
      fechaDesde: '',
      fechaHasta: ''
    });
    this.errorMessage.set(null);
    this.vehicleStore.loadVehicles(undefined);
  }
}
