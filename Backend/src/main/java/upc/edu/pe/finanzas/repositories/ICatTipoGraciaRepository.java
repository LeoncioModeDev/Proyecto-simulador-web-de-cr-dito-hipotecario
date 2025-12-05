package upc.edu.pe.finanzas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upc.edu.pe.finanzas.entities.CatTipoGracia;

@Repository
public interface ICatTipoGraciaRepository extends JpaRepository<CatTipoGracia, Integer> {
}
