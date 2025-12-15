import { Component, input, computed } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { provideNativeDateAdapter } from '@angular/material/core';
import { MatInput } from "@angular/material/input";

@Component({
  selector: 'app-date-range-picker',
  imports: [MatFormFieldModule, MatDatepickerModule, MatInput, ReactiveFormsModule],
  providers: [provideNativeDateAdapter()],
  templateUrl: './date-range-picker.html',
  styleUrl: './date-range-picker.scss'
})
export class DateRangePicker {
  readonly fechaInicio = input.required<FormControl>();
  readonly fechaFin = input.required<FormControl>();
  readonly formGroup = input<FormGroup | null>(null); // FormGroup padre para mostrar errores
  
  readonly minDate = new Date();
  
  // ✅ Computed para mostrar error de rango excedido
  readonly rangoExcedido = computed(() => {
    const group = this.formGroup();
    return group?.hasError('rangoExcedido') ?? false;
  });
}
