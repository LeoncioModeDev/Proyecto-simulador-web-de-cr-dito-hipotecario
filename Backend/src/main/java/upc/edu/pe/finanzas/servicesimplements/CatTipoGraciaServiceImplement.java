package upc.edu.pe.finanzas.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.finanzas.entities.CatTipoGracia;
import upc.edu.pe.finanzas.repositories.ICatTipoGraciaRepository;
import upc.edu.pe.finanzas.servicesinterfaces.ICatTipoGraciaService;

import java.util.List;

@Service
public class CatTipoGraciaServiceImplement implements ICatTipoGraciaService {
    @Autowired
    private ICatTipoGraciaRepository gR;

    @Override
    public List<CatTipoGracia> list() {
        return gR.findAll();
    }

    @Override
    public void insert(CatTipoGracia g) {
        gR.save(g);
    }

    @Override
    public CatTipoGracia listId(int id) {
        return gR.findById(id).orElse(null);
    }

    @Override
    public void update(CatTipoGracia g) {
        gR.save(g);
    }

    @Override
    public void delete(int id) {
        gR.deleteById(id);

    }
}
