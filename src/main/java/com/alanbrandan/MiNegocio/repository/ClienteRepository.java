package com.alanbrandan.MiNegocio.repository;
import com.alanbrandan.MiNegocio.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long> {
    @Query("SELECT c FROM Cliente c WHERE (:nombre IS NULL OR c.nombres LIKE CONCAT('%',:nombre,'%'))" +
            "AND (:id IS NULL OR c.numeroIdentificacion = :id)")
    List<Cliente> getClientesFiltrados(String nombre,String id);

}
