package upc.edu.pe.finanzas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import upc.edu.pe.finanzas.entities.ParamBono;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IParamBonoRepository extends JpaRepository<ParamBono, Integer> {
    @Query("SELECT p FROM ParamBono p " +
            "WHERE :hoy BETWEEN p.vigente_desde AND p.vigente_hasta " +
            "AND :ingreso BETWEEN p.ingreso_min AND p.ingreso_max " +
            "AND :precio <= p.precio_max")
    List<ParamBono> buscarBonoVigente(double ingreso, double precio, LocalDate hoy);
}
