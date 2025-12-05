import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Logeo } from './components/autenticacion/logeo/logeo';
import { Registro } from './components/autenticacion/registro/registro';
import { Home } from './components/modulos/home/home';
import { VerClientes } from './components/modulos/clientes/ver-clientes/ver-clientes';
import { VerInmuebles } from './components/modulos/inmuebles/ver-inmuebles/ver-inmuebles';
import { SimulacionCredito } from './components/modulos/credito/simulacion-credito/simulacion-credito';
import { Dashboard } from './components/modulos/dashboard/dashboard';
import { Configuracion } from './components/modulos/configuracion/configuracion';
import { AgregarEditarClientes } from './components/modulos/clientes/agregar-editar-clientes/agregar-editar-clientes';
import { AgregarEditarInmuebles } from './components/modulos/inmuebles/agregar-editar-inmuebles/agregar-editar-inmuebles';
import { PlanPagos } from './components/modulos/credito/plan-pagos/plan-pagos';
import { VerIfis } from './components/modulos/ifis/ver-ifis/ver-ifis';
import { AgregarEditarIfis } from './components/modulos/ifis/agregar-editar-ifis/agregar-editar-ifis';
import { authGuard } from './guards/auth-guard';

const routes: Routes = [
  { path: "", component: Logeo },
  { path: "login", component: Logeo },
  { path: "registro", component: Registro },
  {
    path: "home",
    component: Home,
    canActivate: [authGuard],
    children: [
      { path: "dashboard", component: Dashboard },
      { path: "clientes", component: VerClientes },
      { path: "clientes/agregar", component: AgregarEditarClientes },
      { path: "clientes/editar/:id", component: AgregarEditarClientes },
      { path: "inmuebles", component: VerInmuebles },
      { path: "inmuebles/agregar", component: AgregarEditarInmuebles },
      { path: "inmuebles/editar/:id", component: AgregarEditarInmuebles },
      { path: "simulacion", component: SimulacionCredito },
      { path: "simulacion/plan-pagos", component: PlanPagos },
      { path: "simulacion/plan-pagos/:id", component: PlanPagos },
      { path: "configuracion", component: Configuracion },
      { path: "ifis", component: VerIfis },
      { path: "ifis/agregar", component: AgregarEditarIfis },
      { path: "ifis/editar/:id", component: AgregarEditarIfis }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }