package upc.edu.pe.finanzas.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.finanzas.dtos.EntidadesFinancierasDTO;
import upc.edu.pe.finanzas.entities.EntidadesFinancieras;
import upc.edu.pe.finanzas.servicesinterfaces.IEntidadesFinancierasService;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/entidadesfinancieras")
public class EntidadesFinancierasController {

    @Autowired
    private IEntidadesFinancierasService efS;

    @GetMapping
    public List<EntidadesFinancierasDTO> listar() {
        return efS.list().stream().map(y -> {
            ModelMapper m = new ModelMapper();
            return m.map(y, EntidadesFinancierasDTO.class);
        }).collect(Collectors.toList());
    }

    @PostMapping
    public void insertar(@RequestBody EntidadesFinancierasDTO dto) {
        ModelMapper m = new ModelMapper();
        EntidadesFinancieras ef = m.map(dto, EntidadesFinancieras.class);
        efS.insert(ef);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarId(@PathVariable("id") Integer id) {
        EntidadesFinancieras ef = efS.listId(id);
        if (ef == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro con el ID: " + id);
        }
        ModelMapper m = new ModelMapper();
        EntidadesFinancierasDTO dto = m.map(ef, EntidadesFinancierasDTO.class);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Integer id) {
        EntidadesFinancieras ef = efS.listId(id);
        if (ef == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro con el ID: " + id);
        }
        efS.delete(id);
        return ResponseEntity.ok("Registro con ID " + id + " eliminado correctamente.");
    }

    @PutMapping
    public ResponseEntity<String> modificar(@RequestBody EntidadesFinancierasDTO dto) {
        ModelMapper m = new ModelMapper();
        EntidadesFinancieras ef = m.map(dto, EntidadesFinancieras.class);

        EntidadesFinancieras existente = efS.listId(ef.getIfi_id());
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe el ID: " + ef.getIfi_id());
        }

        efS.update(ef);
        return ResponseEntity.ok("Registro con ID " + ef.getIfi_id() + " modificado correctamente.");
    }
}
