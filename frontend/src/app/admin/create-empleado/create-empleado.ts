import { Component, inject } from '@angular/core';
import { NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Input } from '@shared/input/input';
import { InputSelect } from '@shared/input-select/input-select';
import { Button } from '@shared/button/button';
import { EmpleadoStore } from '@shared/stores/empleado-store';
import { SucursalStore } from '@shared/stores/sucursal-store';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-empleado',
  imports: [ReactiveFormsModule, Input, InputSelect, Button],
  templateUrl: './create-empleado.html',
  styleUrl: './create-empleado.scss'
})
export class CreateEmpleado {
  readonly fb = inject(NonNullableFormBuilder);
  readonly empleadoStore = inject(EmpleadoStore);
  readonly sucursalStore = inject(SucursalStore);
  readonly router = inject(Router);

  form = this.fb.group({
    nombre: ['', Validators.required],
    apellido: ['', Validators.required],
    dni: [0, [Validators.required, Validators.min(1000000), Validators.max(99999999),Validators.maxLength(9),Validators.minLength(8)]],
    email: ['', [Validators.required, Validators.email]],
    trabajaEnSucursal: ['', Validators.required]
  });

  onSubmit() {
    if (this.form.valid) {
      const formData = {
        nombre: this.form.value.nombre!,
        apellido: this.form.value.apellido!,
        dni: this.form.value.dni!,
        email: this.form.value.email!,
        trabajaEnSucursal: this.form.value.trabajaEnSucursal!
      };
      
      this.empleadoStore.createEmpleado(formData);
      
      // Resetear formulario después del envío
      this.form.reset();
      
      // Redirigir a la lista de empleados después de un breve delay
      setTimeout(() => {
        if (this.empleadoStore.success()) {
          this.router.navigate(['/empleados']);
        }
      }, 1500);
    } else {
      this.form.markAllAsTouched();
    }
  }

  onCancel() {
    this.router.navigate(['/empleados']);
  }
}
