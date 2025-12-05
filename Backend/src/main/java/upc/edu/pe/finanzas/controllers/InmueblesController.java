package upc.edu.pe.finanzas.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.finanzas.dtos.InmueblesDTO;
import upc.edu.pe.finanzas.entities.Inmuebles;
import upc.edu.pe.finanzas.servicesinterfaces.IInmueblesService;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/inmuebles")
public class InmueblesController {

    @Autowired
    private IInmueblesService imS;

    @GetMapping
    public List<InmueblesDTO> listar() {
        return imS.list().stream().map(y -> {
            ModelMapper m = new ModelMapper();
            return m.map(y, InmueblesDTO.class);
        }).collect(Collectors.toList());
    }

    @PostMapping
    public void insertar(@RequestBody InmueblesDTO dto) {
        ModelMapper m = new ModelMapper();
        Inmuebles i = m.map(dto, Inmuebles.class);
        imS.insert(i);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarId(@PathVariable("id") Integer id) {
        Inmuebles i = imS.listId(id);
        if (i == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro con el ID: " + id);
        }
        ModelMapper m = new ModelMapper();
        InmueblesDTO dto = m.map(i, InmueblesDTO.class);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Integer id) {
        Inmuebles i = imS.listId(id);
        if (i == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro con el ID: " + id);
        }
        imS.delete(id);
        return ResponseEntity.ok("Registro con ID " + id + " eliminado correctamente.");
    }

    @PutMapping
    public ResponseEntity<String> modificar(@RequestBody InmueblesDTO dto) {
        ModelMapper m = new ModelMapper();
        Inmuebles i = m.map(dto, Inmuebles.class);

        Inmuebles existente = imS.listId(i.getInmueble_id());
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe el ID: " + i.getInmueble_id());
        }

        imS.update(i);
        return ResponseEntity.ok("Registro con ID " + i.getInmueble_id() + " modificado correctamente.");
    }
}
