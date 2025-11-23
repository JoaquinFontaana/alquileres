import { Component, inject} from '@angular/core';
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
  private readonly fb = inject(NonNullableFormBuilder)

  readonly form = this.fb.group({
    email: ['',[Validators.required,Validators.email]],
    password:['',[Validators.minLength(8),Validators.required]],
    dni:[0,[Validators.required,Validators.minLength(8),Validators.maxLength(9)]],
    nombre:['',[Validators.required]],
    apellido:['',[Validators.required]]
  })

  onSubmit():void{
    const registerData:RegisterClienteRequest = this.form.getRawValue()
    this.authStore.registerCliente(registerData)
  }
}
