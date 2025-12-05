package upc.edu.pe.finanzas.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.finanzas.dtos.IndicadoresFinancierosDTO;
import upc.edu.pe.finanzas.entities.IndicadoresFinancieros;
import upc.edu.pe.finanzas.servicesinterfaces.IIndicadoresFinancierosService;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/indicadores")
public class IndicadoresFinancierosController {

    @Autowired
    private IIndicadoresFinancierosService iS;

    @GetMapping
    public List<IndicadoresFinancierosDTO> listar() {
        return iS.list().stream().map(y -> {
            ModelMapper m = new ModelMapper();
            return m.map(y, IndicadoresFinancierosDTO.class);
        }).collect(Collectors.toList());
    }

    @PostMapping
    public void insertar(@RequestBody IndicadoresFinancierosDTO dto) {
        ModelMapper m = new ModelMapper();
        IndicadoresFinancieros i = m.map(dto, IndicadoresFinancieros.class);
        iS.insert(i);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarId(@PathVariable("id") Integer id) {
        IndicadoresFinancieros i = iS.listId(id);
        if (i == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro con el ID: " + id);
        }
        ModelMapper m = new ModelMapper();
        IndicadoresFinancierosDTO dto = m.map(i, IndicadoresFinancierosDTO.class);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Integer id) {
        IndicadoresFinancieros i = iS.listId(id);
        if (i == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro con el ID: " + id);
        }
        iS.delete(id);
        return ResponseEntity.ok("Registro con ID " + id + " eliminado correctamente.");
    }

    @PutMapping
    public ResponseEntity<String> modificar(@RequestBody IndicadoresFinancierosDTO dto) {
        ModelMapper m = new ModelMapper();
        IndicadoresFinancieros i = m.map(dto, IndicadoresFinancieros.class);

        IndicadoresFinancieros existente = iS.listId(i.getIndicadores_id());
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe el ID: " + i.getIndicadores_id());
        }

        iS.update(i);
        return ResponseEntity.ok("Registro con ID " + i.getIndicadores_id() + " modificado correctamente.");
    }
}
