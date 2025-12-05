package upc.edu.pe.finanzas.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.finanzas.entities.Clientes;
import upc.edu.pe.finanzas.repositories.IClientesRepository;
import upc.edu.pe.finanzas.servicesinterfaces.IClientesService;

import java.util.List;

@Service
public class ClientesServiceImplement implements IClientesService {
    @Autowired
    private IClientesRepository cR;

    @Override
    public List<Clientes> list() {
        return cR.findAll();
    }

    @Override
    public void insert(Clientes c) {
        cR.save(c);
    }

    @Override
    public Clientes listId(int id) {
        return cR.findById(id).orElse(null);
    }

    @Override
    public void update(Clientes c) {
        cR.save(c);
    }

    @Override
    public void delete(int id) {
        cR.deleteById(id);
    }
}
