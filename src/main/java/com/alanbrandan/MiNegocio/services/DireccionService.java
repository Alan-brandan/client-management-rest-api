package com.alanbrandan.MiNegocio.services;

import com.alanbrandan.MiNegocio.domain.Direccion;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DireccionService {

    ResponseEntity<?>  crearDireccion(Long id,Direccion direccion);
    List<Direccion> obtenerDirecciones( Long id);
}
