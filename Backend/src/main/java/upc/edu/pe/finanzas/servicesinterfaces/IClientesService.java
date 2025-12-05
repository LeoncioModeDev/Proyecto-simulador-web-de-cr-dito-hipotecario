package upc.edu.pe.finanzas.servicesinterfaces;

import upc.edu.pe.finanzas.entities.Clientes;

import java.util.List;

public interface IClientesService {
    public List<Clientes> list();
    public void insert(Clientes c);
    public Clientes listId(int id);
    public void update(Clientes c);
    public void delete(int id);
}
