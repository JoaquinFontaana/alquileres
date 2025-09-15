import { Routes } from '@angular/router';
import {VehicleList } from '@vehicles/vehicle-list/vehicle-list'
import { Login } from './login/login';
export const routes: Routes = [
    {
        path:'vehicle-list',
        component:VehicleList
    },
    {
        path: 'login',
        component:Login
    }
];
