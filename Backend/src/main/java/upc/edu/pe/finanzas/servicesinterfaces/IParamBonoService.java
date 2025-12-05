package upc.edu.pe.finanzas.servicesinterfaces;

import upc.edu.pe.finanzas.entities.ParamBono;
import upc.edu.pe.finanzas.entities.PlanesPago;

import java.util.List;

public interface IParamBonoService {
    public List<ParamBono> list();
    public void insert(ParamBono p);
    public ParamBono listId(int id);
    public void update(ParamBono p);
    public void delete(int id);

}
