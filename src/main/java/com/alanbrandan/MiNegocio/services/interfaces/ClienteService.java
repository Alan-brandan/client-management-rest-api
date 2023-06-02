package com.alanbrandan.MiNegocio.services.interfaces;
import com.alanbrandan.MiNegocio.domain.Cliente;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface ClienteService {

    ResponseEntity<?> getClientesFiltrados(String nombre,String id);
    ResponseEntity<?> nuevoCliente( Cliente cliente);
    ResponseEntity<?> modificarCliente(Long id,Cliente cliente);
    ResponseEntity<?> eliminarCliente(Long id);
}
