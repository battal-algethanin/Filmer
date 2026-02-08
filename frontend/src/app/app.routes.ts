import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { ConnectivityTestComponent } from './components/connectivity-test/connectivity-test.component';

export const APP_ROUTES: Routes = [
  {
    path: '',
    component: HomeComponent
  },
  {
    path: 'connection-test',
    component: ConnectivityTestComponent
  },
  {
    path: '**',
    redirectTo: ''
  }
];
