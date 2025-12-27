import { Component, inject } from '@angular/core';
import { NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Input } from '@shared/input/input';
import { Button } from '@shared/button/button';
import { SucursalStore } from '@shared/stores/sucursal-store';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-sucursal',
  imports: [ReactiveFormsModule, Input, Button],
  templateUrl: './create-sucursal.html',
  styleUrl: './create-sucursal.scss'
})
export class CreateSucursal {
  readonly fb = inject(NonNullableFormBuilder);
  readonly sucursalStore = inject(SucursalStore);
  readonly router = inject(Router);

  form = this.fb.group({
    ciudad: ['', Validators.required]
  });

  onSubmit() {
    if (this.form.valid) {
      const ciudad = this.form.value.ciudad!;
      
      this.sucursalStore.createSucursal(ciudad);
      
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
