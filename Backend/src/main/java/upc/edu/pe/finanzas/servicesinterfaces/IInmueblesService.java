package upc.edu.pe.finanzas.servicesinterfaces;

import upc.edu.pe.finanzas.entities.Inmuebles;

import java.util.List;

public interface IInmueblesService {
    public List<Inmuebles> list();
    public void insert(Inmuebles inmu);
    public Inmuebles listId(int id);
    public void update(Inmuebles inmu);
    public void delete(int id);
}
