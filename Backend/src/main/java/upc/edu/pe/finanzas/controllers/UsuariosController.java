package upc.edu.pe.finanzas.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.finanzas.dtos.UsuariosDTO;
import upc.edu.pe.finanzas.entities.Usuarios;
import upc.edu.pe.finanzas.servicesinterfaces.IUsuariosService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    @Autowired
    private IUsuariosService uS;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public List<UsuariosDTO> listar() {
        return uS.list().stream().map(y -> {
            ModelMapper m = new ModelMapper();
            return m.map(y, UsuariosDTO.class);
        }).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> insertar(@RequestBody UsuariosDTO dto) {
        ModelMapper m = new ModelMapper();
        Usuarios u = m.map(dto, Usuarios.class);
        uS.insert(u);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Usuario creado correctamente");
        response.put("user_id", u.getUser_id());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarId(@PathVariable("id") Integer id) {
        Usuarios u = uS.listId(id);
        if (u == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro con el ID: " + id);
        }
        ModelMapper m = new ModelMapper();
        UsuariosDTO dto = m.map(u, UsuariosDTO.class);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable("id") Integer id) {
        Usuarios u = uS.listId(id);
        if (u == null) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "No existe un registro con el ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        uS.delete(id);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Registro con ID " + id + " eliminado correctamente");

        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<Map<String, Object>> modificar(@RequestBody UsuariosDTO dto) {
        // Verificar que existe el usuario
        Usuarios existente = uS.listId(dto.getUser_id());
        if (existente == null) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "No se puede modificar. No existe el ID: " + dto.getUser_id());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        // Actualizar email
        if (dto.getEmail() != null && !dto.getEmail().isEmpty()) {
            existente.setEmail(dto.getEmail());
        }

        // Actualizar contraseña si se proporciona
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            // Encriptar la nueva contraseña
            existente.setPass_hash(passwordEncoder.encode(dto.getPassword()));
        }

        // Guardar cambios
        uS.update(existente);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Registro con ID " + dto.getUser_id() + " modificado correctamente");
        response.put("user_id", existente.getUser_id());
        response.put("email", existente.getEmail());

        return ResponseEntity.ok(response);
    }
}