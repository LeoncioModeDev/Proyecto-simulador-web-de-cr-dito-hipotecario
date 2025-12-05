package upc.edu.pe.finanzas.servicesinterfaces;

import upc.edu.pe.finanzas.entities.PlanesPago;

import java.util.List;

public interface IPlanesPagoService {
    public List<PlanesPago> list();
    public void insert(PlanesPago p);
    public PlanesPago listId(int id);
    public void update(PlanesPago p);
    public void delete(int id);
}