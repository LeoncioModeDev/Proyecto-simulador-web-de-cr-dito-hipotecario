package upc.edu.pe.finanzas.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.finanzas.dtos.ParamMiViviendaDTO;
import upc.edu.pe.finanzas.entities.ParamMiVivienda;
import upc.edu.pe.finanzas.servicesinterfaces.IParamMiViviendaService;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/parammivivienda")
public class ParamMiViviendaController {
    @Autowired
    private IParamMiViviendaService iS;

    @GetMapping
    public List<ParamMiViviendaDTO> listar() {
        return iS.list().stream().map(y -> {
            ModelMapper m = new ModelMapper();
            return m.map(y, ParamMiViviendaDTO.class);
        }).collect(Collectors.toList());
    }

    @PostMapping
    public void insertar(@RequestBody ParamMiViviendaDTO dto) {
        ModelMapper m = new ModelMapper();
        ParamMiVivienda p = m.map(dto, ParamMiVivienda.class);
        iS.insert(p);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarId(@PathVariable("id") Integer id) {
        ParamMiVivienda p = iS.listId(id);
        if (p == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro con el ID: " + id);
        }
        ModelMapper m = new ModelMapper();
        ParamMiViviendaDTO dto = m.map(p, ParamMiViviendaDTO.class);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Integer id) {
        ParamMiVivienda p = iS.listId(id);
        if (p == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro con el ID: " + id);
        }
        iS.delete(id);
        return ResponseEntity.ok("Registro con ID " + id + " eliminado correctamente.");
    }

    @PutMapping
    public ResponseEntity<String> modificar(@RequestBody ParamMiViviendaDTO dto) {
        ModelMapper m = new ModelMapper();
        ParamMiVivienda p = m.map(dto, ParamMiVivienda.class);

        ParamMiVivienda existente = iS.listId(p.getParam_mivivienda_id());
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe el ID: " + p.getParam_mivivienda_id());
        }

        iS.update(p);
        return ResponseEntity.ok("Registro con ID " + p.getParam_mivivienda_id() + " modificado correctamente.");
    }
}
