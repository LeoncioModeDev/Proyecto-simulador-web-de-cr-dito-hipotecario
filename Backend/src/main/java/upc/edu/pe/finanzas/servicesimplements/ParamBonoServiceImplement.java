package upc.edu.pe.finanzas.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import upc.edu.pe.finanzas.entities.ParamBono;
import upc.edu.pe.finanzas.repositories.IParamBonoRepository;
import upc.edu.pe.finanzas.servicesinterfaces.IParamBonoService;

import java.util.List;

@Repository
public class ParamBonoServiceImplement implements IParamBonoService {
    @Autowired
    private IParamBonoRepository bR;

    @Override
    public List<ParamBono> list() {
        return bR.findAll();
    }

    @Override
    public void insert(ParamBono p) {
        bR.save(p);
    }

    @Override
    public ParamBono listId(int id) {
        return bR.findById(id).orElse(null);
    }

    @Override
    public void update(ParamBono p) {
        bR.save(p);
    }

    @Override
    public void delete(int id) {
        bR.deleteById(id);
    }
}
