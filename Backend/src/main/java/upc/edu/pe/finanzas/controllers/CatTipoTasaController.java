package upc.edu.pe.finanzas.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.finanzas.dtos.CatTipoTasaDTO;
import upc.edu.pe.finanzas.entities.CatTipoTasa;
import upc.edu.pe.finanzas.servicesinterfaces.ICatTipoTasaService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catTipoTasa")
@CrossOrigin("*")
public class CatTipoTasaController {

    @Autowired
    private ICatTipoTasaService cS;

    @GetMapping
    public List<CatTipoTasaDTO> listar() {
        return cS.list().stream().map(y -> {
            ModelMapper m = new ModelMapper();
            return m.map(y, CatTipoTasaDTO.class);
        }).collect(Collectors.toList());
    }

    @PostMapping
    public void insertar(@RequestBody CatTipoTasaDTO dto) {
        ModelMapper m = new ModelMapper();
        CatTipoTasa c = m.map(dto, CatTipoTasa.class);
        cS.insert(c);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarId(@PathVariable("id") Integer id) {
        CatTipoTasa c = cS.listId(id);
        if (c == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro con el ID: " + id);
        }
        ModelMapper m = new ModelMapper();
        CatTipoTasaDTO dto = m.map(c, CatTipoTasaDTO.class);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Integer id) {
        CatTipoTasa c = cS.listId(id);
        if (c == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro con el ID: " + id);
        }
        cS.delete(id);
        return ResponseEntity.ok("Registro con ID " + id + " eliminado correctamente.");
    }

    @PutMapping
    public ResponseEntity<String> modificar(@RequestBody CatTipoTasaDTO dto) {
        ModelMapper m = new ModelMapper();
        CatTipoTasa c = m.map(dto, CatTipoTasa.class);

        CatTipoTasa existente = cS.listId(c.getTipo_tasa_id());
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe un registro con el ID: " + c.getTipo_tasa_id());
        }

        cS.update(c);
        return ResponseEntity.ok("Registro con ID " + c.getTipo_tasa_id() + " modificado correctamente.");
    }

}

