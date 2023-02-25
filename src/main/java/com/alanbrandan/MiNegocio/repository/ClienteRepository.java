package com.alanbrandan.MiNegocio.repository;

import com.alanbrandan.MiNegocio.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {

    List<Cliente> findByNombresLikeIgnoreCase(String nombres);
    Cliente findByNumeroIdentificacionLikeIgnoreCase(String id);

}
