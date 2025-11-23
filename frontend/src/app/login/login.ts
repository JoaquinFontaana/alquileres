import { Component, inject, effect } from '@angular/core';
import { NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms'
import { AuthStore } from '@auth-store'
import { LoginRequest } from '@models';
import { Button } from '@shared/button/button';
import { Router, RouterLink} from '@angular/router';
import { Input } from '@shared/input/input';
@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, Button,RouterLink, Input],
  templateUrl: './login.html',
  styleUrl: './login.scss'
})
export class Login {

  private readonly fb = inject(NonNullableFormBuilder)
  private readonly router = inject(Router)
  readonly authStore = inject(AuthStore) 
  
  public form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required,Validators.minLength(8)]]
  })
  
  constructor() {
    // Redirigir al home si el login es exitoso
    effect(() => {
      if (this.authStore.isAuthenticated()) {
        this.router.navigate(['/']);
      }
    });
  }
  
  onSubmit(): void {
    const loguinRequest: LoginRequest = this.form.getRawValue();
    this.authStore.login(loguinRequest)
  }
}
