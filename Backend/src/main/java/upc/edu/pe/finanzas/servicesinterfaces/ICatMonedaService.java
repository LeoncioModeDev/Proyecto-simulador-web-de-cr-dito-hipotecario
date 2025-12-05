package upc.edu.pe.finanzas.servicesinterfaces;

import upc.edu.pe.finanzas.entities.CatMoneda;

import java.util.List;

public interface ICatMonedaService {
    public List<CatMoneda> list();
    public void insert(CatMoneda m);
    public CatMoneda listId(int id);
    public void update(CatMoneda m);
    public void delete(int id);
}
