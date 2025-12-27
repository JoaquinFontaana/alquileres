import { Component, input, computed } from '@angular/core';
import {MatInputModule} from '@angular/material/input';
import {MatSelectModule} from '@angular/material/select';
import {MatFormFieldModule} from '@angular/material/form-field';
import { FormControl, ReactiveFormsModule } from '@angular/forms';

export interface SelectOption {
  value: string;
  label: string;
}

@Component({
  selector: 'app-input-select',
  imports: [MatFormFieldModule,MatInputModule,MatSelectModule,ReactiveFormsModule],
  templateUrl: './input-select.html',
  styleUrl: './input-select.scss'
})
export class InputSelect {
  options = input.required<string[] | SelectOption[]>()
  control = input.required<FormControl>()
  label = input<string>()
  placeHolder = input.required<string>()
  
  // Computed para normalizar opciones
  normalizedOptions = computed(() => {
    const opts = this.options();
    if (opts.length === 0) return [];
    
    // Si es string[], convertir a SelectOption[]
    if (typeof opts[0] === 'string') {
      return (opts as string[]).map(opt => ({ value: opt, label: opt }));
    }
    
    return opts as SelectOption[];
  });
}
