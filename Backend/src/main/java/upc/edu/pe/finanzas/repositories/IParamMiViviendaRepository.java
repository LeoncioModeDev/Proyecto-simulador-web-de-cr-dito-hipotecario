package upc.edu.pe.finanzas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import upc.edu.pe.finanzas.entities.ParamMiVivienda;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IParamMiViviendaRepository extends JpaRepository<ParamMiVivienda, Integer> {
    @Query("SELECT p FROM ParamMiVivienda p " +
            "WHERE :hoy BETWEEN p.vigente_desde AND p.vigente_hasta")
    List<ParamMiVivienda> buscarParamVigente(LocalDate hoy);
}
