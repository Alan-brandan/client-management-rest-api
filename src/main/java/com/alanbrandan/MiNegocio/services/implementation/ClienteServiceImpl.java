package com.alanbrandan.MiNegocio.services.implementation;

import com.alanbrandan.MiNegocio.domain.Cliente;
import com.alanbrandan.MiNegocio.repository.ClienteRepository;
import com.alanbrandan.MiNegocio.services.interfaces.ClienteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository repository;

    @Override
    public ResponseEntity<?> getClientesFiltrados( String nombre,String id) {
        List<Cliente> results = this.repository.getClientesFiltrados(nombre,id);
        if(results.isEmpty()){
            return ResponseEntity.badRequest().body("no se encontro clientes con ese nombre/numero de identificacion");
        }
        return ResponseEntity.ok(results);
    }

    @Override
    public ResponseEntity<?> nuevoCliente(Cliente cliente) {
        List<Cliente> results = this.repository.getClientesFiltrados(null,cliente.getNumeroIdentificacion());
        if(!results.isEmpty()){
            return ResponseEntity.badRequest().body("ya existe un cliente con el numero de identificacion ingresado");
        }
        return ResponseEntity.ok(repository.save(cliente));
    }

    @Override
    public ResponseEntity<?> modificarCliente(Long id,Cliente cliente) {

        Optional<Cliente> result = this.repository.findById(id);
        if(result.isPresent()){

            if(!result.get().getNumeroIdentificacion().equals(cliente.getNumeroIdentificacion())){
                List<Cliente> results = this.repository.getClientesFiltrados(null,cliente.getNumeroIdentificacion());
                if(results!=null){
                    return ResponseEntity.badRequest().body("ya existe un cliente con el numero de identificacion ingresado");
                }
            }

            result.get().setNombres(cliente.getNombres());
            result.get().setCorreo(cliente.getCorreo());
            result.get().setNumeroCelular(cliente.getNumeroCelular());
            result.get().setNumeroIdentificacion(cliente.getNumeroIdentificacion());
            result.get().setTipoIdentificacion(cliente.getTipoIdentificacion());
            return ResponseEntity.ok(repository.save(result.get()));
        }
        return ResponseEntity.badRequest().body("no existe un cliente con el ID ingresado");
    }

    @Override
    public ResponseEntity<?> eliminarCliente(Long id) {

        Optional<Cliente> encontrados = this.repository.findById(id);
        if(encontrados.isPresent()){
            this.repository.deleteById(id);
            return ResponseEntity.ok("se ha eliminado el Cliente con id: " + id);
        }
        return ResponseEntity.badRequest().body("no existe un cliente con el ID ingresado");
    }
}
