package com.alanbrandan.MiNegocio.services;

import com.alanbrandan.MiNegocio.domain.Cliente;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ClienteService {

    List<Cliente> obtenerClientes();
    List<Cliente> obtenerClientesConParam(String nombre,String id);
    Cliente nuevoCliente( Cliente cliente);
    Cliente modificarCliente(Cliente cliente);
    ResponseEntity<?> eliminarCliente(Long id);
}
