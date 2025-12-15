import { Component, input } from '@angular/core';
import {MatInputModule} from '@angular/material/input';
import {MatSelectModule} from '@angular/material/select';
import {MatFormFieldModule} from '@angular/material/form-field';
import { FormControl, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-input-select',
  imports: [MatFormFieldModule,MatInputModule,MatSelectModule,ReactiveFormsModule],
  templateUrl: './input-select.html',
  styleUrl: './input-select.scss'
})
export class InputSelect {
  options = input.required<string[]>()
  control = input.required<FormControl>()
  label = input<string>()
  placeHolder = input.required<string>()
}
