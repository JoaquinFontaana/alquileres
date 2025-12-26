import { Component, inject, effect, input, computed } from '@angular/core';
import { NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Input } from '@shared/input/input';
import { InputSelect } from '@shared/input-select/input-select';
import { Button } from '@shared/button/button';
import { VehiclesStore } from '@shared/stores/vehicles-store';
import { VehiclesData } from '@vehicles/services/vehicles-data';
import { Router } from '@angular/router';
import { SucursalStore } from '@shared/stores/sucursal-store';
import { Vehicle, VehicleUpdateDTO } from '@models';

@Component({
  selector: 'app-update-vehicle',
  imports: [ReactiveFormsModule, Input, InputSelect, Button],
  templateUrl: './update-vehicle.html',
  styleUrl: './update-vehicle.scss'
})
export class UpdateVehicle {
  readonly fb = inject(NonNullableFormBuilder);
  readonly vehiclesStore = inject(VehiclesStore);
  readonly vehiclesService = inject(VehiclesData);
  readonly router = inject(Router);
  readonly sucursalStore = inject(SucursalStore);
  
  // Route param con transformación automática a string
  readonly id = input.required<number, string>({
    transform: Number
  });
  
  selectedFile: File | null = null;
  rembolsos: string[] = [];
  
  // Computed para obtener el vehículo del store
  readonly vehicle = computed(() => {
    const vehicleId = this.id();
    return this.vehiclesStore.getVehicleById(vehicleId)()
  });

  constructor() {
    // Cargar rembolsos
    this.loadRembolsos();
    
    // Effect para poblar el formulario cuando se carga el vehículo
    effect(() => {
      const vehicle = this.vehicle();
      if (vehicle) {
        this.populateForm(vehicle);
      }
    });
    
    // Effect para manejar el éxito en la actualización
    effect(() => {
      const success = this.vehiclesStore.success();
      if (success) {
        setTimeout(() => {
          this.router.navigate(['/vehicle-list']);
        }, 1500);
      }
    });
  }

  loadRembolsos() {
    this.vehiclesService.getRembolsos().subscribe({
      next: (rembolsos) => this.rembolsos = rembolsos,
      error: (err) => console.error('Error cargando rembolsos:', err)
    });
  }

  populateForm(vehicle: Vehicle) {
    this.form.patchValue({
      patente: vehicle.patente,
      capacidad: vehicle.capacidad,
      marca: vehicle.marca,
      modelo: vehicle.modelo,
      precioPorDia: vehicle.precioPorDia,
      categoria: vehicle.categoria,
      rembolso: vehicle.rembolso,
      estado: vehicle.estado,
      sucursal: vehicle.sucursal
    });
  }

  form = this.fb.group({
    patente: [{ value: '', disabled: true }, Validators.required],
    capacidad: [0, [Validators.required, Validators.min(1)]],
    marca: [{ value: '', disabled: true }, Validators.required],
    modelo: [{ value: '', disabled: true }, Validators.required],
    precioPorDia: [0, [Validators.required, Validators.min(0)]],
    categoria: ['', Validators.required],
    rembolso: ['', Validators.required],
    estado: [{ value: '', disabled: true }, Validators.required],
    sucursal: ['', Validators.required]
  });
  
  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
    }
  }
  
  onSubmit() {
    if (this.form.valid) {
      const formValue = this.form.getRawValue();
      
      const vehicleData = new VehicleUpdateDTO({
        capacidad: formValue.capacidad,
        precioPorDia: formValue.precioPorDia,
        categoria: formValue.categoria,
        rembolso: formValue.rembolso,
        sucursal: formValue.sucursal,
        imagen: this.selectedFile || undefined
      });
      
      this.vehiclesStore.updateVehicle({data: vehicleData,id:this.id()});
    } else {
      this.form.markAllAsTouched();
    }
  }

  onCancel() {
    this.router.navigate(['/vehicle-list']);
  }
}
