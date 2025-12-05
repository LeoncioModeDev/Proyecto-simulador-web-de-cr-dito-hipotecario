package upc.edu.pe.finanzas.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.finanzas.entities.Usuarios;
import upc.edu.pe.finanzas.repositories.IUsuariosRepository;
import upc.edu.pe.finanzas.servicesinterfaces.IUsuariosService;

import java.util.List;

@Service
public class UsuariosServiceImplement implements IUsuariosService {
    @Autowired
    private IUsuariosRepository uR;

    @Override
    public List<Usuarios> list() {
        return uR.findAll();
    }

    @Override
    public void insert(Usuarios u) {
        uR.save(u);
    }

    @Override
    public Usuarios listId(int id) {
        return uR.findById(id).orElse(null);
    }

    @Override
    public void update(Usuarios u) {
        uR.save(u);
    }

    @Override
    public void delete(int id) {
        uR.deleteById(id);
    }

    @Override
    public Usuarios findByUsername(String username) {
        return uR.findByUsername(username);
    }
}
