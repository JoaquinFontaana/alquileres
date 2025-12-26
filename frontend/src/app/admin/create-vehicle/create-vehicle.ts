import { Component, inject, OnInit } from '@angular/core';
import {NonNullableFormBuilder, ReactiveFormsModule,Validators } from '@angular/forms';
import { Input } from '@shared/input/input';
import { InputSelect } from '@shared/input-select/input-select';
import { Button } from '@shared/button/button';
import { VehiclesStore } from '@shared/stores/vehicles-store';
import { VehiclesData } from '@vehicles/services/vehicles-data';
import { Router } from '@angular/router';
import { SucursalStore } from '@shared/stores/sucursal-store';
import { VehicleCreateDTO } from '@models';

@Component({
  selector: 'app-create-vehicle',
  imports: [ReactiveFormsModule, Input, InputSelect, Button],
  templateUrl: './create-vehicle.html',
  styleUrl: './create-vehicle.scss'
})
export class CreateVehicle implements OnInit {
  readonly fb = inject(NonNullableFormBuilder)
  readonly vehiclesStore = inject(VehiclesStore)
  readonly vehiclesService = inject(VehiclesData)
  readonly router = inject(Router)
  readonly sucursalStore = inject(SucursalStore)
  
  selectedFile: File | null = null;
  rembolsos: string[] = [];

  ngOnInit() {
    this.vehiclesService.getRembolsos().subscribe({
      next: (rembolsos) => this.rembolsos = rembolsos,
      error: (err) => console.error('Error cargando rembolsos:', err)
    });
  }

  get estadosDisponibles(): string[] {
    return this.vehiclesStore.estados().filter(
      estado => estado !== 'ALQUILADO' && estado !== 'BAJA'
    );
  }
  
  form = this.fb.group({
    patente:['',Validators.required],
    capacidad:[0,[Validators.required, Validators.min(1)]],
    marca:['',Validators.required],
    modelo:['',Validators.required],
    precioPorDia:[0,[Validators.required, Validators.min(0)]],
    categoria:['',Validators.required],
    rembolso:['',Validators.required],
    estado:['',Validators.required],
    sucursal:['',Validators.required]
  })
  
  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
    }
  }
  
  onSubmit() {
    if (this.form.valid && this.selectedFile) {
      const formValue = this.form.getRawValue();
      
      const vehicleData = new VehicleCreateDTO({
        patente: formValue.patente,
        capacidad: formValue.capacidad,
        marca: formValue.marca,
        modelo: formValue.modelo,
        precioPorDia: formValue.precioPorDia,
        categoria: formValue.categoria,
        rembolso: formValue.rembolso,
        estado: formValue.estado,
        sucursal: formValue.sucursal,
        imagen: this.selectedFile
      });
      
      this.vehiclesStore.createVehicle(vehicleData);
      
      // Resetear formulario y archivo después del envío exitoso
      setTimeout(() => {
        if (this.vehiclesStore.success()) {
          this.form.reset();
          this.selectedFile = null;
          const fileInput = document.getElementById('imagen') as HTMLInputElement;
          if (fileInput) fileInput.value = '';
        }
      }, 100);
    } else {
      this.form.markAllAsTouched();
      if (!this.selectedFile) {
        alert('Por favor seleccione una imagen');
      }
    }
  }
  
  onCancel() {
    this.router.navigate(['/vehicle-list']);
  }
}
