import { Component, inject, computed } from '@angular/core';
import { EmpleadoStore } from '@shared/stores/empleado-store';
import { SucursalStore } from '@shared/stores/sucursal-store';
import { InputSelect } from '@shared/input-select/input-select';
import { CommonModule } from '@angular/common';
import { NonNullableFormBuilder, ReactiveFormsModule } from '@angular/forms';
import { Button } from '@shared/button/button';
import { Router } from '@angular/router';

@Component({
  selector: 'app-empleados',
  imports: [InputSelect, CommonModule, ReactiveFormsModule,Button],
  templateUrl: './empleados.html',
  styleUrl: './empleados.scss'
})
export class Empleados {
  readonly fb = inject(NonNullableFormBuilder);
  readonly empleadoStore = inject(EmpleadoStore);
  readonly sucursalStore = inject(SucursalStore);
  readonly router = inject(Router);
  
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
  disabled = computed(()=>{
    return !this.sucursalControl.value && !this.estadoControl.value
  })
  clearFilters() {
    this.sucursalControl.setValue('');
    this.estadoControl.setValue('');
  }

  navigateToCreate() {
    this.router.navigate(['/admin/empleados/create']);
  }

  darDeBaja(email: string) {
    if (confirm('¿Está seguro que desea dar de baja este empleado?')) {
      this.empleadoStore.darDeBajaEmpleado(email);
    }
  }
}
