package upc.edu.pe.finanzas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import upc.edu.pe.finanzas.entities.PlanesPago;

import java.util.List;

@Repository
public interface IPlanesPagoRepository extends JpaRepository<PlanesPago, Integer> {
    @Query("SELECT p FROM PlanesPago p WHERE p.simulacionesCredito.simulacion_id = :simulacionId ORDER BY p.periodo ASC")
    List<PlanesPago> findBySimulacionId(@Param("simulacionId") int simulacionId);
}
