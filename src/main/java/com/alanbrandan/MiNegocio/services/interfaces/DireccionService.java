package com.alanbrandan.MiNegocio.services.interfaces;
import com.alanbrandan.MiNegocio.domain.Direccion;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface DireccionService {

    ResponseEntity<?>  crearDireccion(Long id,Direccion direccion);
    ResponseEntity<?> obtenerDirecciones( Long id);
}
