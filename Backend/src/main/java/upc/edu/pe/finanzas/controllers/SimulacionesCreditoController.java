package upc.edu.pe.finanzas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.finanzas.dtos.SimulacionCreditoDTO;
import upc.edu.pe.finanzas.dtos.SimulacionCreditoRequest;
import upc.edu.pe.finanzas.dtos.SimulacionCreditoResponse;
import upc.edu.pe.finanzas.dtos.VerificarCreditoResponse;
import upc.edu.pe.finanzas.entities.SimulacionesCredito;
import upc.edu.pe.finanzas.servicesinterfaces.ISimulacionesCreditoService;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/simulaciones")
public class SimulacionesCreditoController {

    @Autowired
    private ISimulacionesCreditoService simulacionService;

    @GetMapping
    public ResponseEntity<List<SimulacionesCredito>> listarTodo() {
        return ResponseEntity.ok(simulacionService.list());
    }

    @PostMapping
    public ResponseEntity<Void> insertar(@RequestBody SimulacionesCredito simulacion) {
        simulacionService.insert(simulacion);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SimulacionesCredito> obtenerPorId(@PathVariable int id) {
        return ResponseEntity.ok(simulacionService.listId(id));
    }

    @PutMapping
    public ResponseEntity<Void> actualizar(@RequestBody SimulacionesCredito simulacion) {
        simulacionService.update(simulacion);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        simulacionService.delete(id);
        return ResponseEntity.ok().build();
    }

    // ============================================
    // 🔹 ENDPOINTS PARA SIMULACIÓN
    // ============================================

    @PostMapping("/verificar")
    public ResponseEntity<VerificarCreditoResponse> verificarCredito(
            @RequestBody SimulacionCreditoRequest request) {
        VerificarCreditoResponse response = simulacionService.verificarCredito(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/simular")
    public ResponseEntity<SimulacionCreditoResponse> simularCredito(
            @RequestBody SimulacionCreditoRequest request) {
        SimulacionCreditoResponse response = simulacionService.simularCredito(request);
        return ResponseEntity.ok(response);
    }

    // 🔹 NUEVO: Obtener simulación completa (indicadores + plan de pagos)
    @GetMapping("/{id}/completa")
    public ResponseEntity<SimulacionCreditoResponse> obtenerSimulacionCompleta(
            @PathVariable int id) {
        SimulacionCreditoResponse response = simulacionService.obtenerSimulacionCompleta(id);
        return ResponseEntity.ok(response);
    }

    // ============================================
    // 🔹 NUEVO: Obtener últimas N simulaciones
    // ============================================
    /**
     * Obtiene las últimas N simulaciones ordenadas por fecha descendente
     * GET /simulaciones/ultimas?cantidad=10
     */
    @GetMapping("/ultimas")
    public ResponseEntity<List<SimulacionCreditoDTO>> obtenerUltimas(
            @RequestParam(value = "cantidad", defaultValue = "10") int cantidad) {
        try {
            // Obtener todas las simulaciones y ordenarlas por fecha descendente
            List<SimulacionesCredito> simulaciones = simulacionService.list();

            // Convertir a DTO, ordenar por fecha descendente y limitar cantidad
            List<SimulacionCreditoDTO> dtos = simulaciones.stream()
                    .sorted((a, b) -> b.getFecha_simulacion().compareTo(a.getFecha_simulacion()))
                    .limit(cantidad)
                    .map(this::mapearADTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    // ============================================
    // 🔹 MÉTODO AUXILIAR: Mapear a DTO
    // ============================================
    private SimulacionCreditoDTO mapearADTO(SimulacionesCredito simulacion) {
        SimulacionCreditoDTO dto = new SimulacionCreditoDTO();
        dto.setSimulacion_id(simulacion.getSimulacion_id());
        dto.setCuota_inicial_pct(simulacion.getCuota_inicial_pct());
        dto.setCuota_inicial_monto(simulacion.getCuota_inicial_monto());
        dto.setPlazo_meses(simulacion.getPlazo_meses());
        dto.setGastos_adicionales(simulacion.getGastos_adicionales());
        dto.setTasa_anual(simulacion.getTasa_anual());
        dto.setCapitalizacion_m(simulacion.getCapitalizacion_m());
        dto.setMeses_gracia(simulacion.getMeses_gracia());
        dto.setBfh_aplica(simulacion.isBfh_aplica());
        dto.setBfh_monto(simulacion.getBfh_monto());
        dto.setMonto_credito(simulacion.getMonto_credito());
        dto.setTasa_efectiva_mensual(simulacion.getTasa_efectiva_mensual());
        dto.setEstado(simulacion.getEstado());
        dto.setFecha_simulacion(simulacion.getFecha_simulacion());
        return dto;
    }
}