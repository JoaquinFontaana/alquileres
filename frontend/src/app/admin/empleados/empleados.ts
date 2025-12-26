import { Component, inject, computed } from '@angular/core';
import { EmpleadoStore } from '@shared/stores/empleado-store';
import { SucursalStore } from '@shared/stores/sucursal-store';
import { InputSelect } from '@shared/input-select/input-select';
import { CommonModule } from '@angular/common';
import { NonNullableFormBuilder, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-empleados',
  imports: [InputSelect, CommonModule, ReactiveFormsModule],
  templateUrl: './empleados.html',
  styleUrl: './empleados.scss'
})
export class Empleados {
  readonly fb = inject(NonNullableFormBuilder);
  readonly empleadoStore = inject(EmpleadoStore);
  readonly sucursalStore = inject(SucursalStore);
  
  // Form controls para los filtros
  readonly sucursalControl = this.fb.control('');
  readonly estadoControl = this.fb.control('');
  
  // Estados disponibles
  readonly estados = ['ACTIVO', 'DE_BAJA'];
  
  // Empleados filtrados
  readonly empleadosFiltrados = computed(() => {
    const empleados = this.empleadoStore.entities();
    const sucursal = this.sucursalControl.value || '';
    const estado = this.estadoControl.value || '';
    
    return empleados.filter(emp => {
      const matchSucursal = !sucursal || emp.trabajaEnSucursal === sucursal;
      const matchEstado = !estado || emp.estado === estado;
      return matchSucursal && matchEstado;
    });
  });
  
  clearFilters() {
    this.sucursalControl.setValue('');
    this.estadoControl.setValue('');
  }
}
