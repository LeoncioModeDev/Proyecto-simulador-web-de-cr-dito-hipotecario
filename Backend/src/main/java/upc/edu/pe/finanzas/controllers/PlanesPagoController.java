package upc.edu.pe.finanzas.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.finanzas.dtos.PlanesPagoDTO;
import upc.edu.pe.finanzas.entities.PlanesPago;
import upc.edu.pe.finanzas.servicesinterfaces.IPlanesPagoService;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/planespago")
public class PlanesPagoController {

    @Autowired
    private IPlanesPagoService ppS;

    @GetMapping
    public List<PlanesPagoDTO> listar() {
        return ppS.list().stream().map(y -> {
            ModelMapper m = new ModelMapper();
            return m.map(y, PlanesPagoDTO.class);
        }).collect(Collectors.toList());
    }

    @PostMapping
    public void insertar(@RequestBody PlanesPagoDTO dto) {
        ModelMapper m = new ModelMapper();
        PlanesPago p = m.map(dto, PlanesPago.class);
        ppS.insert(p);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarId(@PathVariable("id") Integer id) {
        PlanesPago p = ppS.listId(id);
        if (p == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro con el ID: " + id);
        }
        ModelMapper m = new ModelMapper();
        PlanesPagoDTO dto = m.map(p, PlanesPagoDTO.class);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Integer id) {
        PlanesPago p = ppS.listId(id);
        if (p == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro con el ID: " + id);
        }
        ppS.delete(id);
        return ResponseEntity.ok("Registro con ID " + id + " eliminado correctamente.");
    }

    @PutMapping
    public ResponseEntity<String> modificar(@RequestBody PlanesPagoDTO dto) {
        ModelMapper m = new ModelMapper();
        PlanesPago p = m.map(dto, PlanesPago.class);

        PlanesPago existente = ppS.listId(p.getPlan_id());
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe el ID: " + p.getPlan_id());
        }

        ppS.update(p);
        return ResponseEntity.ok("Registro con ID " + p.getPlan_id() + " modificado correctamente.");
    }
}
