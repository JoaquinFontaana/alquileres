import { Component, input,computed } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import {MatFormFieldModule} from '@angular/material/form-field'
import {MatInputModule} from '@angular/material/input'

@Component({
  selector: 'app-input',
  imports: [ReactiveFormsModule,MatInputModule,MatFormFieldModule],
  templateUrl: './input.html',
  styleUrl: './input.scss'
})
export class Input {
  readonly control = input.required<FormControl>();
  readonly type = input<string>('text');
  readonly placeholder = input<string>('');
  readonly id = input<string>('');
  readonly label = input<string>('');
  readonly class = input<string>('form-input');
  readonly containerClass = input<string>('input-container')
  readonly appearance = input<'fill' | 'outline'>('outline')
  readonly hint = input<string>()
  readonly maxlength = input<number | null >(null)
}
