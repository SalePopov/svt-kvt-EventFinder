import { Routes } from '@angular/router';

import { LoginComponent } from './pages/login/login';
import { RegisterRequestComponent } from './pages/register-request/register-request';
import { DashboardComponent } from './pages/dashboard/dashboard';
import { LocationListComponent } from './pages/location-list/location-list';
import { LocationDetailComponent } from './pages/location-detail/location-detail';
import { LocationFormComponent } from './pages/location-form/location-form';
import { EventListComponent } from './pages/event-list/event-list';
import { EventFormComponent } from './pages/event-form/event-form';
import { RegistrationRequestsComponent } from './pages/admin/registration-requests/registration-requests';

import { authGuard } from './guards/auth-guard';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register-request', component: RegisterRequestComponent },

  { path: 'locations/new', component: LocationFormComponent, canActivate: [authGuard] },
  { path: 'locations/:id/edit', component: LocationFormComponent, canActivate: [authGuard] },
  { path: 'locations/:id', component: LocationDetailComponent },
  { path: 'locations', component: LocationListComponent },
  { path: 'locations/:locationId/events/new', component: EventFormComponent, canActivate: [authGuard] },

  { path: 'events/:id/edit', component: EventFormComponent, canActivate: [authGuard] },
  { path: 'events', component: EventListComponent },

  { path: 'dashboard', component: DashboardComponent, canActivate: [authGuard] },
  { path: 'admin/requests', component: RegistrationRequestsComponent, canActivate: [authGuard] },

  { path: '', redirectTo: '/locations', pathMatch: 'full' },
  { path: '**', redirectTo: '/locations' }
];
