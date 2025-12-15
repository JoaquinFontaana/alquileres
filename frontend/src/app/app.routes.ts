import { Routes } from '@angular/router';
import {VehicleList } from 'src/app/cliente/vehicle-list/vehicle-list'
import { Login } from './login/login';
import { Home } from './home/home';
import { Register } from './register/register';
import { RentVehicle } from './cliente/rent-vehicle/rent-vehicle';

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
        component: RentVehicle
    }
];
