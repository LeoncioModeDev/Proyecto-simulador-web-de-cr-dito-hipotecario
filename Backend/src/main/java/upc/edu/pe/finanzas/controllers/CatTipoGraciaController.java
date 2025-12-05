package upc.edu.pe.finanzas.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.finanzas.dtos.CatMonedaDTO;
import upc.edu.pe.finanzas.dtos.CatTipoGraciaDTO;
import upc.edu.pe.finanzas.entities.CatMoneda;
import upc.edu.pe.finanzas.entities.CatTipoGracia;
import upc.edu.pe.finanzas.servicesinterfaces.ICatMonedaService;
import upc.edu.pe.finanzas.servicesinterfaces.ICatTipoGraciaService;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/cattipogracia")
public class CatTipoGraciaController {

    @Autowired
    private ICatTipoGraciaService cS;

    @GetMapping
    public List<CatTipoGraciaDTO> listar() {
        return cS.list().stream().map(y -> {
            ModelMapper m = new ModelMapper();
            return m.map(y, CatTipoGraciaDTO.class);
        }).collect(Collectors.toList());
    }

    @PostMapping
    public void insertar(@RequestBody CatTipoGraciaDTO dto) {
        ModelMapper m = new ModelMapper();
        CatTipoGracia c = m.map(dto, CatTipoGracia.class);
        cS.insert(c);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarId(@PathVariable("id") Integer id) {
        CatTipoGracia c = cS.listId(id);
        if (c == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro con el ID: " + id);
        }
        ModelMapper m = new ModelMapper();
        CatTipoGraciaDTO dto = m.map(c, CatTipoGraciaDTO.class);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Integer id) {
        CatTipoGracia c = cS.listId(id);
        if (c == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro con el ID: " + id);
        }
        cS.delete(id);
        return ResponseEntity.ok("Registro con ID " + id + " eliminado correctamente.");
    }

    @PutMapping
    public ResponseEntity<String> modificar(@RequestBody CatTipoGraciaDTO dto) {
        ModelMapper m = new ModelMapper();
        CatTipoGracia c = m.map(dto, CatTipoGracia.class);

        CatTipoGracia existente = cS.listId(c.getTipo_gracia_id());
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe un registro con el ID: " + c.getTipo_gracia_id());
        }

        cS.update(c);
        return ResponseEntity.ok("Registro con ID " + c.getTipo_gracia_id() + " modificado correctamente.");
    }
}
