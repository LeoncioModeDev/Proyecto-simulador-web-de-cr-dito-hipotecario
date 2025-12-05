package upc.edu.pe.finanzas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upc.edu.pe.finanzas.entities.IndicadoresFinancieros;

@Repository
public interface IIndicadoresFinancierosRepository extends JpaRepository<IndicadoresFinancieros, Integer> {
}
