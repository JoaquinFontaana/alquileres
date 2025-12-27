import { Routes } from '@angular/router';
import { Login } from './login/login';
import { Home } from './home/home';
import { Register } from './register/register';
import { RentVehicle } from './cliente/rent-vehicle/rent-vehicle';
import { authGuard } from '@guards/auth-guard';
import { clienteGuard } from '@guards/cliente-guard';
import { adminGuard } from '@guards/admin-guard';
import { CreateEmpleado } from '@admin/create-empleado/create-empleado';
import { CreateVehicle } from '@admin/create-vehicle/create-vehicle';
import { SucursalList } from '@admin/sucursal-list/sucursal-list';
import { CreateSucursal } from '@admin/create-sucursal/create-sucursal';
import { UpdateVehicle } from '@admin/update-vehicle/update-vehicle';
import { Stats } from '@admin/stats/stats';
import { VehicleList } from '@vehicles/vehicle-list/vehicle-list';
import { Empleados } from '@admin/empleados/empleados';
import { MyRentals } from './cliente/my-rentals/my-rentals';
export const routes: Routes = [
    {
        path: '',
        component: Home
    },
    {
        path:'vehicle-list',
        component:VehicleList
    },
    {
        path: 'login',
        component:Login
    },
    {
        path:'register',
        component:Register
    },
    {
        path: 'cliente',
        canActivateChild: [authGuard,clienteGuard],
        children:[
            {path: 'rent-vehicle/:id',component:RentVehicle},
            {path: 'my-rentals',component:MyRentals}
        ]
    },
    {
        path: 'admin',
        canActivateChild: [authGuard,adminGuard],
        children:[
            {path: 'sucursales',component:SucursalList},
            {path: 'sucursales/create',component:CreateSucursal},
            {path:'vehicles/create', component:CreateVehicle},
            {path:'vehicles/update/:id', component: UpdateVehicle},
            {path: 'stats', component: Stats},
            {path:'empleados',component:Empleados},
            {path: 'empleados/create',component:CreateEmpleado}
        ]
    }
];
