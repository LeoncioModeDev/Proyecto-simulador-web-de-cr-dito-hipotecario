package upc.edu.pe.finanzas.servicesinterfaces;

import upc.edu.pe.finanzas.entities.CatTipoGracia;

import java.util.List;

public interface ICatTipoGraciaService {
    public List<CatTipoGracia> list();
    public void insert(CatTipoGracia g);
    public CatTipoGracia listId(int id);
    public void update(CatTipoGracia g);
    public void delete(int id);
}
