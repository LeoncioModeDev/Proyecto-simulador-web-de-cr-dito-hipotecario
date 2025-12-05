package upc.edu.pe.finanzas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upc.edu.pe.finanzas.entities.Usuarios;

@Repository
public interface IUsuariosRepository extends JpaRepository<Usuarios, Integer> {
    Usuarios findByUsername(String username);
}
