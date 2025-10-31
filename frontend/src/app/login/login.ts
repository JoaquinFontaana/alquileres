import { Component, inject } from '@angular/core';
import {NonNullableFormBuilder, ReactiveFormsModule, Validators} from '@angular/forms'
import {AuthStore} from '@auth-store'
import { LoginRequest } from '@models';
import { Button } from '@shared/button/button';
@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule,Button],
  templateUrl: './login.html',
  styleUrl: './login.scss'
})
export class Login {

  private readonly fb = inject(NonNullableFormBuilder)
  readonly authStore = inject(AuthStore) 
  public form = this.fb.group({
    email: ['',[Validators.required,Validators.email]],
    password: ['',[Validators.required]]
  })
  
  onSubmit():void{
    const loguinRequest: LoginRequest = this.form.getRawValue();
    this.authStore.login(loguinRequest)
  }
}
