export interface Usuario {
  user_id?: number;  // ← Cambiar a opcional
  username: string;
  email: string;
  password?: string;
  pass_hash?: string;
}

export interface UsuarioActualizacion {
  email: string;
  password: string;
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  tokenType?: string;
  expiresIn?: number;
  usuario: Usuario;
}