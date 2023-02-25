package com.alanbrandan.MiNegocio.services;

import com.alanbrandan.MiNegocio.domain.Cliente;
import com.alanbrandan.MiNegocio.repository.ClienteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService{

    private final ClienteRepository repository;
    public ClienteServiceImpl(ClienteRepository repository) { this.repository=repository; }

    @Override
    public List<Cliente> obtenerClientes() {
        return this.repository.findAll();
    }

    @Override
    public List<Cliente> obtenerClientesConParam( String nombre,String id) {
        Cliente resultbyid = this.repository.findByNumeroIdentificacionLikeIgnoreCase(id);
        List<Cliente> resultbyname = this.repository.findByNombresLikeIgnoreCase(nombre);
        if(resultbyid!=null && !resultbyname.isEmpty()){
            throw new IllegalArgumentException("solo se permite ingresar nombre o numero de identificacion como parametro, no ambos al mismo tiempo");
        }
        if(resultbyid==null){
            if(resultbyname.isEmpty()){
                throw new IllegalArgumentException("no se encontro clientes con ese nombre/numero de identificacion");
            }
            return resultbyname;
        }
        return new ArrayList<>(Arrays.asList(resultbyid));
    }

    @Override
    public Cliente nuevoCliente(Cliente cliente) {
        Optional<Cliente> encontrados = this.repository.findAll().stream().filter(p->p.getNumeroIdentificacion().equals(cliente.getNumeroIdentificacion())).findAny();
        if(encontrados.isEmpty()){
            return this.repository.save(cliente);
        }
        throw new IllegalArgumentException("ya existe un cliente con ese numero de identificación");
    }

    @Override
    public Cliente modificarCliente(Cliente cliente) {

        Optional<Cliente> ocliente = this.repository.findById(cliente.getId());
        if(ocliente.isPresent()){

            if(!ocliente.get().getNumeroIdentificacion().equals(cliente.getNumeroIdentificacion())){
                Cliente resultbyid = this.repository.findByNumeroIdentificacionLikeIgnoreCase(cliente.getNumeroIdentificacion());
                if(resultbyid!=null){
                    throw new IllegalArgumentException("ya existe un cliente con el numero de identificacion ingresado");
                }
            }
            ocliente.get().setNombres(cliente.getNombres());
            ocliente.get().setCorreo(cliente.getCorreo());
            ocliente.get().setNumeroCelular(cliente.getNumeroCelular());
            ocliente.get().setNumeroIdentificacion(cliente.getNumeroIdentificacion());
            ocliente.get().setTipoIdentificacion(cliente.getTipoIdentificacion());
            return this.repository.save(ocliente.get());
        }
        throw new IllegalArgumentException("no existe un cliente con el ID ingresado");
    }

    @Override
    public ResponseEntity<?> eliminarCliente(Long id) {

        Optional<Cliente> encontrados = this.repository.findAll().stream().filter(p->p.getId().equals(id)).findAny();
        if(encontrados.isPresent()){
            this.repository.deleteById(id);
            return ResponseEntity.ok("se ha eliminado el Cliente con id:" + id);
        }
        return ResponseEntity.badRequest().body("no existe un cliente con el ID ingresado");
    }
}
