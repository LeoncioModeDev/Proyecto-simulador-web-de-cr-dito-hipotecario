package upc.edu.pe.finanzas.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.finanzas.dtos.ParamBonoDTO;
import upc.edu.pe.finanzas.entities.ParamBono;
import upc.edu.pe.finanzas.servicesinterfaces.IParamBonoService;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/parambono")
public class ParamBonoController {
    @Autowired
    private IParamBonoService iS;

    @GetMapping
    public List<ParamBonoDTO> listar() {
        return iS.list().stream().map(y -> {
            ModelMapper m = new ModelMapper();
            return m.map(y, ParamBonoDTO.class);
        }).collect(Collectors.toList());
    }

    @PostMapping
    public void insertar(@RequestBody ParamBonoDTO dto) {
        ModelMapper m = new ModelMapper();
        ParamBono p = m.map(dto, ParamBono.class);
        iS.insert(p);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarId(@PathVariable("id") Integer id) {
        ParamBono p = iS.listId(id);
        if (p == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro con el ID: " + id);
        }
        ModelMapper m = new ModelMapper();
        ParamBonoDTO dto = m.map(p, ParamBonoDTO.class);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Integer id) {
        ParamBono p = iS.listId(id);
        if (p == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro con el ID: " + id);
        }
        iS.delete(id);
        return ResponseEntity.ok("Registro con ID " + id + " eliminado correctamente.");
    }

    @PutMapping
    public ResponseEntity<String> modificar(@RequestBody ParamBonoDTO dto) {
        ModelMapper m = new ModelMapper();
        ParamBono p = m.map(dto, ParamBono.class);

        ParamBono existente = iS.listId(p.getParam_bono_id());
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe el ID: " + p.getParam_bono_id());
        }

        iS.update(p);
        return ResponseEntity.ok("Registro con ID " + p.getParam_bono_id() + " modificado correctamente.");
    }
}
