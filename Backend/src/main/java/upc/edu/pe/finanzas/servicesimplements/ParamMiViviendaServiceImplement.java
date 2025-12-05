package upc.edu.pe.finanzas.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.finanzas.entities.ParamMiVivienda;
import upc.edu.pe.finanzas.repositories.IParamMiViviendaRepository;
import upc.edu.pe.finanzas.servicesinterfaces.IParamMiViviendaService;

import java.util.List;

@Service
public class ParamMiViviendaServiceImplement implements IParamMiViviendaService {

    @Autowired
    private IParamMiViviendaRepository mR;

    @Override
    public List<ParamMiVivienda> list() {
        return mR.findAll();
    }

    @Override
    public void insert(ParamMiVivienda p) {
        mR.save(p);
    }

    @Override
    public ParamMiVivienda listId(int id) {
        return mR.findById(id).orElse(null);
    }

    @Override
    public void update(ParamMiVivienda p) {
        mR.save(p);
    }

    @Override
    public void delete(int id) {
        mR.deleteById(id);
    }
}
