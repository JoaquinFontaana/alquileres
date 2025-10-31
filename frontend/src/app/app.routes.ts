import { Routes } from '@angular/router';
import {VehicleList } from '@vehicles/vehicle-list/vehicle-list'
import { Login } from './login/login';
import { Home } from './home/home';

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
    }
];
