package upc.edu.pe.finanzas.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.finanzas.dtos.LoginRequest;
import upc.edu.pe.finanzas.dtos.LoginResponse;
import upc.edu.pe.finanzas.dtos.UsuariosDTO;
import upc.edu.pe.finanzas.entities.Usuarios;
import upc.edu.pe.finanzas.security.JwtTokenProvider;
import upc.edu.pe.finanzas.servicesinterfaces.IUsuariosService;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IUsuariosService usuariosService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Login del usuario
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Validar que no estén vacíos
        if (loginRequest.getUsername() == null || loginRequest.getUsername().isEmpty() ||
                loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Usuario y contraseña son requeridos");
        }

        // Buscar usuario por username
        Usuarios usuario = usuariosService.findByUsername(loginRequest.getUsername());

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Usuario o contraseña incorrectos");
        }

        // Verificar contraseña
        if (!passwordEncoder.matches(loginRequest.getPassword(), usuario.getPass_hash())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Usuario o contraseña incorrectos");
        }

        // Generar token
        String token = jwtTokenProvider.generateToken(usuario.getUsername());

        // Mapear a DTO
        ModelMapper mapper = new ModelMapper();
        UsuariosDTO usuarioDTO = mapper.map(usuario, UsuariosDTO.class);

        // Retornar respuesta con token
        LoginResponse loginResponse = new LoginResponse(
                token,
                86400,  // 24 horas en segundos
                usuarioDTO
        );

        return ResponseEntity.ok(loginResponse);
    }

    /**
     * Registro de nuevo usuario
     */
    @PostMapping("/registro")
    public ResponseEntity<?> registro(@RequestBody UsuariosDTO usuarioDTO) {
        // Validar que no estén vacíos
        if (usuarioDTO.getUsername() == null || usuarioDTO.getUsername().isEmpty() ||
                usuarioDTO.getPassword() == null || usuarioDTO.getPassword().isEmpty() ||
                usuarioDTO.getEmail() == null || usuarioDTO.getEmail().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Username, email y contraseña son requeridos");
        }

        // Verificar si el usuario ya existe
        Usuarios existente = usuariosService.findByUsername(usuarioDTO.getUsername());
        if (existente != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El usuario ya existe");
        }

        try {
            // Mapear DTO a entidad
            ModelMapper mapper = new ModelMapper();
            Usuarios usuario = mapper.map(usuarioDTO, Usuarios.class);

            // Encriptar contraseña
            usuario.setPass_hash(passwordEncoder.encode(usuarioDTO.getPassword()));

            // Guardar usuario
            usuariosService.insert(usuario);

            // Generar token
            String token = jwtTokenProvider.generateToken(usuario.getUsername());

            // Mapear a DTO nuevamente para la respuesta
            UsuariosDTO usuarioDTORespuesta = mapper.map(usuario, UsuariosDTO.class);

            // Retornar respuesta
            LoginResponse loginResponse = new LoginResponse(
                    token,
                    86400,
                    usuarioDTORespuesta
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(loginResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar el usuario: " + e.getMessage());
        }
    }
}