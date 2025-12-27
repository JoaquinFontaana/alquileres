import { Component, inject } from '@angular/core';
import { AuthStore } from '@auth-store';
import { Router } from '@angular/router';
import { Button } from '@shared/button/button';
@Component({
  selector: 'app-sidenav',
  imports: [Button],
  templateUrl: './sidenav.html',
  styleUrl: './sidenav.scss'
})
export class Sidenav {
  readonly authStore = inject(AuthStore)
  readonly router = inject(Router)

  toCreateVehicle():void{
    this.router.navigate(['/admin/vehicles/create'])
  }
  toSucursales():void{
    this.router.navigate(['/admin/sucursales'])
  }
  toCreateSucursal():void{
    this.router.navigate(['/admin/sucursales/create'])
  }
  toEmpleados():void{
    this.router.navigate(['/admin/empleados'])
  }
  toStats():void{
    this.router.navigate(['/admin/stats'])
  }
  toCreateEmpleado():void{
    this.router.navigate(['/admin/empleados/create'])
  }
  toVehicles():void{
    this.router.navigate(['/vehicle-list'])
  }
}
