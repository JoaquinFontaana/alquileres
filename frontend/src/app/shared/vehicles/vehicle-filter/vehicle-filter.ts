import { Component, inject, signal, computed, effect } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, AbstractControl, ValidationErrors } from '@angular/forms';
import { Button } from '@shared/button/button';
import { VehiclesStore } from '@shared/stores/vehicles-store';
import { SucursalStore } from '@shared/stores/sucursal-store';
import { VehicleFilter as VehicleFilterModel, VehicleFilterFormData } from '@models';
import { DateRangePicker } from '@shared/date-range-picker/date-range-picker';
import { InputSelect } from '@shared/input-select/input-select';
import { AuthStore } from '@auth-store';

@Component({
  selector: 'app-vehicle-filter',
  imports: [ReactiveFormsModule, Button, DateRangePicker, InputSelect],
  templateUrl: './vehicle-filter.html',
  styleUrl: './vehicle-filter.scss'
})
export class VehicleFilter {
  private readonly fb = inject(FormBuilder);
  readonly vehicleStore = inject(VehiclesStore);
  readonly sucursalStore = inject(SucursalStore);
  // Fecha mínima (hoy)
  readonly today = new Date().toISOString().split('T')[0];
  readonly authStore = inject(AuthStore);
  // Mensaje de error
  readonly errorMessage = signal<string | null>(null);

  public form = this.fb.group({
    nombreSucursal: [''],
    categorias: [''],
    fechaDesde: [''],
    fechaHasta: [''],
    estados:['']
  }, { validators: [this.dateRangeValidator()] });

  //Exponer las fechas del filtro públicamente (ya formateadas)
  readonly fechaInicio = computed(() => {
    const fecha = this.form.controls.fechaDesde.value;
    console.log(fecha)
    return this.formatDate(fecha);
  });
  
  readonly fechaFin = computed(() => {
    const fecha = this.form.controls.fechaHasta.value;
    return this.formatDate(fecha);
  });

  // Effect para aplicar filtro automáticamente cuando se seleccionen ambas fechas
  constructor() {
    effect(() => {
      const fechaDesde = this.form.controls.fechaDesde.value;
      const fechaHasta = this.form.controls.fechaHasta.value;
      
      // Si ambas fechas están seleccionadas y el form es válido, aplicar filtro
      if (fechaDesde && fechaHasta && this.form.valid) {
        this.applyFilters();
      }
    });
  }

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

  // Convertir fecha a formato ISO (YYYY-MM-DD) para el backend
  private formatDate(date: Date | string | null): string | null {
    if (!date) return null;
    if (typeof date === 'string') return date;
    const d = new Date(date);
    const year = d.getFullYear();
    const month = String(d.getMonth() + 1).padStart(2, '0');
    const day = String(d.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  }

  applyFilters(): void {
    this.errorMessage.set(null);

    if (this.form.invalid) {
      this.handleFormErrors();
      return;
    }

    const formData = this.form.value as VehicleFilterFormData;
    const isAdmin = this.authStore.hasRole("ADMIN") && this.authStore.isAuthenticated();
    const filter = VehicleFilterModel.fromFormData(formData, this.formatDate.bind(this), isAdmin);
    
    this.vehicleStore.loadVehicles(filter);
  }

  private handleFormErrors(): void {
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
