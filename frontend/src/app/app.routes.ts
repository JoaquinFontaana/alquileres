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
import { UpdateVehicle } from '@admin/update-vehicle/update-vehicle';
import { Stats } from '@admin/stats/stats';
import { VehicleList } from '@vehicles/vehicle-list/vehicle-list';
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
        path: 'cliente/rent-vehicle/:id',
        component: RentVehicle,
        canActivate:[authGuard,clienteGuard]
    },
    {
        path: 'admin',
        canActivateChild: [authGuard,adminGuard],
        children:[
            {path: 'sucursales',component:SucursalList},
            {path:'vehicles/create', component:CreateVehicle},
            {path:'vehicles/update', component: UpdateVehicle},
            {path: 'stats', component: Stats},
            {path: 'empleados/create',component:CreateEmpleado}
        ]
    }
];
