package upc.edu.pe.finanzas.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.finanzas.entities.PlanesPago;
import upc.edu.pe.finanzas.repositories.IPlanesPagoRepository;
import upc.edu.pe.finanzas.servicesinterfaces.IPlanesPagoService;

import java.util.List;

@Service
public class PlanesPagoServiceImplement implements IPlanesPagoService {
    @Autowired
    private IPlanesPagoRepository pR;

    @Override
    public List<PlanesPago> list() {
        return pR.findAll();
    }

    @Override
    public void insert(PlanesPago p) {
        pR.save(p);
    }

    @Override
    public PlanesPago listId(int id) {
        return pR.findById(id).orElse(null);
    }

    @Override
    public void update(PlanesPago p) {
        pR.save(p);
    }

    @Override
    public void delete(int id) {
        pR.deleteById(id);
    }
}
