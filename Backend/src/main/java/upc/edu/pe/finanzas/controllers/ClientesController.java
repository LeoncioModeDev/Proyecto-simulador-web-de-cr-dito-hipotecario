package upc.edu.pe.finanzas.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.finanzas.dtos.ClientesDTO;
import upc.edu.pe.finanzas.entities.Clientes;
import upc.edu.pe.finanzas.servicesinterfaces.IClientesService;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/clientes")
public class ClientesController {

    @Autowired
    private IClientesService cS;

    @GetMapping
    public List<ClientesDTO> listar() {
        return cS.list().stream().map(y -> {
            ModelMapper m = new ModelMapper();
            return m.map(y, ClientesDTO.class);
        }).collect(Collectors.toList());
    }

    @PostMapping
    public void insertar(@RequestBody ClientesDTO dto) {
        ModelMapper m = new ModelMapper();
        Clientes c = m.map(dto, Clientes.class);
        cS.insert(c);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarId(@PathVariable("id") Integer id) {
        Clientes c = cS.listId(id);
        if (c == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro con el ID: " + id);
        }
        ModelMapper m = new ModelMapper();
        ClientesDTO dto = m.map(c, ClientesDTO.class);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Integer id) {
        Clientes c = cS.listId(id);
        if (c == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro con el ID: " + id);
        }
        cS.delete(id);
        return ResponseEntity.ok("Registro con ID " + id + " eliminado correctamente.");
    }

    @PutMapping
    public ResponseEntity<String> modificar(@RequestBody ClientesDTO dto) {
        ModelMapper m = new ModelMapper();
        Clientes c = m.map(dto, Clientes.class);

        Clientes existente = cS.listId(c.getCliente_id());
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe el ID: " + c.getCliente_id());
        }

        cS.update(c);
        return ResponseEntity.ok("Registro con ID " + c.getCliente_id() + " modificado correctamente.");
    }
}
