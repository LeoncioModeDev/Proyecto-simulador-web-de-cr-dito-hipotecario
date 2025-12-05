package upc.edu.pe.finanzas.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.finanzas.entities.EntidadesFinancieras;
import upc.edu.pe.finanzas.repositories.IEntidadesFinancierasRepository;
import upc.edu.pe.finanzas.servicesinterfaces.IEntidadesFinancierasService;

import java.util.List;

@Service
public class EntidadesFinancierasServiceImplement implements IEntidadesFinancierasService {
    @Autowired
    private IEntidadesFinancierasRepository eR;

    @Override
    public List<EntidadesFinancieras> list() {
        return eR.findAll();
    }

    @Override
    public void insert(EntidadesFinancieras e) {
        eR.save(e);
    }

    @Override
    public EntidadesFinancieras listId(int id) {
        return eR.findById(id).orElse(null);
    }

    @Override
    public void update(EntidadesFinancieras e) {
        eR.save(e);
    }

    @Override
    public void delete(int id) {
        eR.deleteById(id);
    }
}
