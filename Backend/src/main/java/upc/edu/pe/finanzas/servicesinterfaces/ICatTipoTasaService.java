package upc.edu.pe.finanzas.servicesinterfaces;

import upc.edu.pe.finanzas.entities.CatTipoTasa;

import java.util.List;

public interface ICatTipoTasaService {
    public List<CatTipoTasa> list();
    public void insert(CatTipoTasa t);
    public CatTipoTasa listId(int id);
    public void update(CatTipoTasa t);
    public void delete(int id);
}
