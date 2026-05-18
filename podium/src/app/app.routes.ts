import { Routes } from '@angular/router';
import { authGuard } from './guards/auth.guard';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { FacturasComponent } from './components/facturas/facturas.component';
import { GastosComponent } from './components/gastos/gastos.component';
import { PedidosComponent } from './components/pedidos/pedidos.component';
import { DeudasComponent } from './components/deudas/deudas.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'dashboard', component: DashboardComponent, canActivate: [authGuard] },
  { path: 'facturas', component: FacturasComponent, canActivate: [authGuard] },
  { path: 'gastos', component: GastosComponent, canActivate: [authGuard] },
  { path: 'deudas', component: DeudasComponent, canActivate: [authGuard] },
  { path: 'pedidos', component: PedidosComponent, canActivate: [authGuard] },
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: '**', redirectTo: '/login' }
];
