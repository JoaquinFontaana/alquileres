import { Component, inject } from '@angular/core';
import { NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Input } from '@shared/input/input';
import { Button } from '@shared/button/button';
import { SucursalStore } from '@shared/stores/sucursal-store';
import { Router } from '@angular/router';
import { Map } from '@shared/map/map';
import { Sucursal } from '@models';
import { DecimalPipe } from '@angular/common';

@Component({
  selector: 'app-create-sucursal',
  imports: [ReactiveFormsModule, Input, Button,Map, DecimalPipe],
  templateUrl: './create-sucursal.html',
  styleUrl: './create-sucursal.scss'
})
export class CreateSucursal {
  readonly fb = inject(NonNullableFormBuilder);
  readonly sucursalStore = inject(SucursalStore);
  readonly router = inject(Router);

  form = this.fb.group({
    ciudad: ['', Validators.required],
    latitud: this.fb.control<number| null>(null,[Validators.required]),
    longitud: this.fb.control<number | null>(null,[Validators.required])
  });

  // Esta función se ejecuta cuando el usuario hace click en el mapa
  onUbicacionSeleccionada(coords: { lat: number; lng: number }) {
    this.form.patchValue({
      latitud: coords.lat,
      longitud: coords.lng
    });
    this.form.markAsDirty(); // Habilitar botón de guardar
  }

  onSubmit() {
    if (this.form.valid) {

      const sucursalData = this.form.getRawValue() as Sucursal;
      
      this.sucursalStore.createSucursal(sucursalData);
      
      // Resetear formulario después del envío
      this.form.reset();
      
      // Redirigir a la lista de sucursales después de un breve delay
      setTimeout(() => {
        if (this.sucursalStore.success()) {
          this.router.navigate(['/admin/sucursales']);
        }
      }, 1500);
    } else {
      this.form.markAllAsTouched();
    }
  }

  onCancel() {
    this.router.navigate(['/admin/sucursales']);
  }
}
