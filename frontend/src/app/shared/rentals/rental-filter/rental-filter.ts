import { Component, inject, signal, output, input, effect } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, AbstractControl, ValidationErrors } from '@angular/forms';
import { Button } from '@shared/button/button';
import { SucursalStore } from '@shared/stores/sucursal-store';
import { RentalFilter as RentalFilterModel } from '../../rentals/services/rentals-data';
import { DateRangePicker } from '@shared/date-range-picker/date-range-picker';
import { InputSelect } from '@shared/input-select/input-select';
import { Input } from '@shared/input/input';
import { AuthStore } from '@auth-store';

@Component({
  selector: 'app-rental-filter',
  imports: [ReactiveFormsModule, Button, DateRangePicker, InputSelect, Input],
  templateUrl: './rental-filter.html',
  styleUrl: './rental-filter.scss'
})
export class RentalFilter {
  private readonly fb = inject(FormBuilder);
  readonly sucursalStore = inject(SucursalStore);
  readonly authStore = inject(AuthStore);
  
  // Inputs para configurar qué filtros mostrar
  readonly showSucursalFilter = input<boolean>(true);
  readonly showEstadoAlquilerFilter = input<boolean>(true);
  readonly showEstadoPagoFilter = input<boolean>(false); // Solo admin/empleado
  readonly showClienteFilter = input<boolean>(false); // Solo admin/empleado
  readonly showDateFilter = input<boolean>(true);
  
  // Output para emitir los filtros aplicados
  readonly filtersApplied = output<RentalFilterModel>();
  
  // Fecha mínima (hoy)
  readonly today = new Date().toISOString().split('T')[0];
  
  // Mensaje de error
  readonly errorMessage = signal<string | null>(null);
  
  // Opciones de estados
  readonly estadosAlquiler = [
    { value: 'CONFIRMACION_PENDIENTE', label: 'Confirmación Pendiente' },
    { value: 'RETIRO_PENDIENTE', label: 'Retiro Pendiente' },
    { value: 'EN_USO', label: 'En Uso' },
    { value: 'FINALIZADO', label: 'Finalizado' },
    { value: 'CANCELADO', label: 'Cancelado' }
  ];
  
  readonly estadosPago = [
    { value: 'PAGADO', label: 'Pagado' },
    { value: 'REMBOLSADO', label: 'Rembolsado' },
    { value: 'PENDIENTE', label: 'Pendiente' }
  ];

  public form = this.fb.group({
    nombreSucursal: [''],
    estadoAlquilerEnum: [''],
    estadoPago: [''],
    clienteMail: [''],
    fechaDesde: [''],
    fechaHasta: ['']
  }, { validators: [this.dateRangeValidator()] });

  constructor() {
    // Effect para validar fechas automáticamente
    effect(() => {
      const fechaDesde = this.form.controls.fechaDesde.value;
      const fechaHasta = this.form.controls.fechaHasta.value;
      
      if (fechaDesde && fechaHasta && this.form.valid) {
        this.errorMessage.set(null);
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
        this.errorMessage.set('Debes seleccionar ambas fechas');
        return { ambasFechasRequeridas: true };
      }

      // Si ambas están vacías, es válido
      if (!fechaDesde && !fechaHasta) {
        return null;
      }

      const desde = new Date(fechaDesde);
      const hasta = new Date(fechaHasta);

      // Validar que fecha hasta sea después de fecha desde
      if (hasta < desde) {
        this.errorMessage.set('La fecha de fin debe ser posterior a la fecha de inicio');
        return { fechaInvalida: true };
      }

      return null;
    };
  }

  applyFilters(): void {
    if (!this.form.valid) {
      this.errorMessage.set('Por favor corrige los errores en el formulario');
      return;
    }

    const formValue = this.form.value;
    const filters: RentalFilterModel = {};

    // Solo incluir filtros con valor
    if (formValue.nombreSucursal) {
      filters.nombreSucursal = formValue.nombreSucursal;
    }
    
    if (formValue.estadoAlquilerEnum) {
      filters.estadoAlquilerEnum = [formValue.estadoAlquilerEnum];
    }
    
    if (formValue.estadoPago) {
      filters.estadoPago = [formValue.estadoPago];
    }
    
    if (formValue.clienteMail) {
      filters.clienteMail = formValue.clienteMail;
    }
    
    if (formValue.fechaDesde) {
      filters.fechaDesde = formValue.fechaDesde;
    }
    
    if (formValue.fechaHasta) {
      filters.fechaHasta = formValue.fechaHasta;
    }

    this.errorMessage.set(null);
    this.filtersApplied.emit(filters);
  }

  clearFilters(): void {
    this.form.reset({
      nombreSucursal: '',
      estadoAlquilerEnum: '',
      estadoPago: '',
      clienteMail: '',
      fechaDesde: '',
      fechaHasta: ''
    });
    this.errorMessage.set(null);
    this.filtersApplied.emit({});
  }
}
