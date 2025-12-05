import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth-service';

export const jwtInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  // Rutas que no requieren token
  const rutasPublicas = ['/auth/login', '/auth/registro'];
  
  // Si es una ruta pública, continuar sin agregar token
  if (rutasPublicas.some(ruta => req.url.includes(ruta))) {
    return next(req);
  }

  // Obtener token actual
  let token = authService.obtenerToken();

  // Si hay token, agregarlo al request
  if (token && token !== "") {
    let clonRequest = req.clone({
      headers: req.headers.set("Authorization", "Bearer " + token)
    });
    return next(clonRequest);
  }

  return next(req);
};