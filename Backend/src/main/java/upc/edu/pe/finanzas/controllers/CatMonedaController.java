package upc.edu.pe.finanzas.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.finanzas.dtos.CatMonedaDTO;
import upc.edu.pe.finanzas.entities.CatMoneda;
import upc.edu.pe.finanzas.servicesinterfaces.ICatMonedaService;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/moneda")
public class CatMonedaController {

    @Autowired
    private ICatMonedaService cmS;

    @GetMapping
    public List<CatMonedaDTO> listar() {
        return cmS.list().stream().map(y -> {
            ModelMapper m = new ModelMapper();
            return m.map(y, CatMonedaDTO.class);
        }).collect(Collectors.toList());
    }

    @PostMapping
    public void insertar(@RequestBody CatMonedaDTO dto) {
        ModelMapper m = new ModelMapper();
        CatMoneda c = m.map(dto, CatMoneda.class);
        cmS.insert(c);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarId(@PathVariable("id") Integer id) {
        CatMoneda c = cmS.listId(id);
        if (c == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro con el ID: " + id);
        }
        ModelMapper m = new ModelMapper();
        CatMonedaDTO dto = m.map(c, CatMonedaDTO.class);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Integer id) {
        CatMoneda c = cmS.listId(id);
        if (c == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro con el ID: " + id);
        }
        cmS.delete(id);
        return ResponseEntity.ok("Registro con ID " + id + " eliminado correctamente.");
    }

    @PutMapping
    public ResponseEntity<String> modificar(@RequestBody CatMonedaDTO dto) {
        ModelMapper m = new ModelMapper();
        CatMoneda c = m.map(dto, CatMoneda.class);

        CatMoneda existente = cmS.listId(c.getMoneda_id());
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe el ID: " + c.getMoneda_id());
        }

        cmS.update(c);
        return ResponseEntity.ok("Registro con ID " + c.getMoneda_id() + " modificado correctamente.");
    }
}
