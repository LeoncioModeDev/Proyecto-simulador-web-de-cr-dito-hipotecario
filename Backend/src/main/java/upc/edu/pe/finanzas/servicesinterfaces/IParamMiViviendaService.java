package upc.edu.pe.finanzas.servicesinterfaces;

import upc.edu.pe.finanzas.entities.ParamMiVivienda;

import java.util.List;

public interface IParamMiViviendaService {
    public List<ParamMiVivienda> list();
    public void insert(ParamMiVivienda p);
    public ParamMiVivienda listId(int id);
    public void update(ParamMiVivienda p);
    public void delete(int id);
}
