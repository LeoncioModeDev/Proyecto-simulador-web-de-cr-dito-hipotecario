package upc.edu.pe.finanzas.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.finanzas.entities.IndicadoresFinancieros;
import upc.edu.pe.finanzas.repositories.IIndicadoresFinancierosRepository;
import upc.edu.pe.finanzas.servicesinterfaces.IIndicadoresFinancierosService;

import java.util.List;

@Service
public class IndicadoresFinancierosServiceImplement implements IIndicadoresFinancierosService {
    @Autowired
    private IIndicadoresFinancierosRepository fR;

    @Override
    public List<IndicadoresFinancieros> list() {
        return fR.findAll();
    }

    @Override
    public void insert(IndicadoresFinancieros f) {
        fR.save(f);
    }

    @Override
    public IndicadoresFinancieros listId(int id) {
        return fR.findById(id).orElse(null);
    }

    @Override
    public void update(IndicadoresFinancieros f) {
        fR.save(f);
    }

    @Override
    public void delete(int id) {
        fR.deleteById(id);
    }
}
