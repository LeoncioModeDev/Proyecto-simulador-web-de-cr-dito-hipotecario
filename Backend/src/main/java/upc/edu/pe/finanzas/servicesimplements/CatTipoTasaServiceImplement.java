package upc.edu.pe.finanzas.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.finanzas.entities.CatTipoTasa;
import upc.edu.pe.finanzas.repositories.ICatTipoTasaRepository;
import upc.edu.pe.finanzas.servicesinterfaces.ICatTipoTasaService;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CatTipoTasaServiceImplement implements ICatTipoTasaService {
    @Autowired
    private ICatTipoTasaRepository tR;

    @Override
    public List<CatTipoTasa> list() {
        return tR.findAll();
    }

    @Override
    public void insert(CatTipoTasa t) {
        tR.save(t);
    }

    @Override
    public CatTipoTasa listId(int id) {
        return tR.findById(id).orElse(null);
    }

    @Override
    public void update(CatTipoTasa t) {
        tR.save(t);
    }

    @Override
    public void delete(int id) {
        tR.deleteById(id);
    }

    public BigDecimal convertirTasaNominalAMensual(BigDecimal tasaNominalAnual) {
        final int n = 12; // Periodos por año (mensual)
        BigDecimal tasaMensual = BigDecimal.ONE.add(tasaNominalAnual.divide(BigDecimal.valueOf(n), 10, BigDecimal.ROUND_HALF_UP));
        tasaMensual = tasaMensual.pow(n).subtract(BigDecimal.ONE);
        return tasaMensual;
    }

    // Método para convertir tasa efectiva de cualquier periodo a tasa efectiva mensual
    public BigDecimal convertirTasaEfectivaAMensual(BigDecimal tasaEfectivaPeriodo) {
        final int n = 12; // Periodos por año (mensual)
        BigDecimal tasaMensual = tasaEfectivaPeriodo.add(BigDecimal.ONE).pow(1 / n).subtract(BigDecimal.ONE);
        return tasaMensual;
    }
}
