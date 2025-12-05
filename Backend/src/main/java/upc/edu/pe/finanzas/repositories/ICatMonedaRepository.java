package upc.edu.pe.finanzas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upc.edu.pe.finanzas.entities.CatMoneda;

@Repository
public interface ICatMonedaRepository extends JpaRepository<CatMoneda, Integer> {
}
