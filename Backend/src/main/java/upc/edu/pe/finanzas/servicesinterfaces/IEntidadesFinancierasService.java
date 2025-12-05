package upc.edu.pe.finanzas.servicesinterfaces;

import upc.edu.pe.finanzas.entities.EntidadesFinancieras;

import java.util.List;

public interface IEntidadesFinancierasService {
    public List<EntidadesFinancieras> list();
    public void insert(EntidadesFinancieras e);
    public EntidadesFinancieras listId(int id);
    public void update(EntidadesFinancieras e);
    public void delete(int id);
}
