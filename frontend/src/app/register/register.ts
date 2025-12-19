import { Component, inject, effect } from '@angular/core';
import { Router } from '@angular/router';
import { AuthStore } from '@auth-store';
import { NonNullableFormBuilder, ReactiveFormsModule,Validators } from '@angular/forms';
import { RegisterClienteRequest } from '@models';
import { Button } from '@shared/button/button';
import { Input } from '@shared/input/input';
@Component({
  selector: 'app-register',
  imports: [ReactiveFormsModule,Button,Input],
  templateUrl: './register.html',
  styleUrl: './register.scss'
})
export class Register {
  readonly authStore = inject(AuthStore);
  private readonly router = inject(Router);
  private readonly fb = inject(NonNullableFormBuilder);
  private registrationSuccess = false;

  readonly form = this.fb.group({
    email: ['',[Validators.required,Validators.email]],
    password:['',[Validators.minLength(8),Validators.required]],
    dni:[0,[Validators.required,Validators.minLength(8),Validators.maxLength(9)]],
    nombre:['',[Validators.required]],
    apellido:['',[Validators.required]]
  })

  constructor() {
    effect(() => {
      const isLoading = this.authStore.isLoading();
      const error = this.authStore.error();
      
      // Si el registro fue exitoso (no hay error, no está cargando y se marcó como éxito)
      if (this.registrationSuccess && !isLoading && !error) {
        this.router.navigate(['/login']);
      }
    });
  }

  onSubmit():void{
    this.registrationSuccess = true;
    const registerData:RegisterClienteRequest = this.form.getRawValue()
    this.authStore.registerCliente(registerData)
  }
}
