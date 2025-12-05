package upc.edu.pe.finanzas.servicesinterfaces;

import upc.edu.pe.finanzas.dtos.SimulacionCreditoRequest;
import upc.edu.pe.finanzas.dtos.SimulacionCreditoResponse;
import upc.edu.pe.finanzas.dtos.VerificarCreditoResponse;
import upc.edu.pe.finanzas.entities.SimulacionesCredito;

import java.util.List;

public interface ISimulacionesCreditoService {
    public List<SimulacionesCredito> list();
    public void insert(SimulacionesCredito s);
    public SimulacionesCredito listId(int id);
    public void update(SimulacionesCredito s);
    public void delete(int id);

    VerificarCreditoResponse verificarCredito(SimulacionCreditoRequest request);

    // 🔹 Este método devuelve la respuesta completa Y guarda en BD
    SimulacionCreditoResponse simularCredito(SimulacionCreditoRequest request);

    // 🔹 NUEVO: Obtener una simulación completa por ID (con indicadores y plan de pagos)
    SimulacionCreditoResponse obtenerSimulacionCompleta(int simulacionId);
}