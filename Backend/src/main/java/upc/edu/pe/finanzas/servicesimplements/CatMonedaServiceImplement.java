package upc.edu.pe.finanzas.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.finanzas.entities.CatMoneda;
import upc.edu.pe.finanzas.repositories.ICatMonedaRepository;
import upc.edu.pe.finanzas.servicesinterfaces.ICatMonedaService;

import java.util.List;

@Service
public class CatMonedaServiceImplement implements ICatMonedaService {
    @Autowired
    private ICatMonedaRepository mR;

    @Override
    public List<CatMoneda> list() {
        return mR.findAll();
    }

    @Override
    public void insert(CatMoneda m) {
        mR.save(m);
    }

    @Override
    public CatMoneda listId(int id) {
        return mR.findById(id).orElse(null);
    }

    @Override
    public void update(CatMoneda m) {
        mR.save(m);
    }

    @Override
    public void delete(int id) {
        mR.deleteById(id);
    }
}
