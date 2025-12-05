package upc.edu.pe.finanzas.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.finanzas.entities.Inmuebles;
import upc.edu.pe.finanzas.repositories.IInmueblesRepository;
import upc.edu.pe.finanzas.servicesinterfaces.IInmueblesService;

import java.util.List;

@Service
public class InmueblesServiceImplement implements IInmueblesService {
    @Autowired
    private IInmueblesRepository inmuR;

    @Override
    public List<Inmuebles> list() {
        return inmuR.findAll();
    }

    @Override
    public void insert(Inmuebles inmu) {
        inmuR.save(inmu);
    }

    @Override
    public Inmuebles listId(int id) {
        return inmuR.findById(id).orElse(null);
    }

    @Override
    public void update(Inmuebles inmu) {
        inmuR.save(inmu);
    }

    @Override
    public void delete(int id) {
        inmuR.deleteById(id);
    }
}
