package com.alanbrandan.MiNegocio.services;
import com.alanbrandan.MiNegocio.domain.Cliente;
import com.alanbrandan.MiNegocio.domain.Direccion;
import com.alanbrandan.MiNegocio.repository.ClienteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DireccionServiceImpl implements DireccionService{

    private final ClienteRepository repository;
    public DireccionServiceImpl(ClienteRepository repository) { this.repository=repository; }

    @Override
    public ResponseEntity<?> crearDireccion(Long id,Direccion direccion) {
        Optional<Cliente> client = this.repository.findById(id);
        if(client.isPresent()){
            List<Direccion> nuevalista = new ArrayList<>();
            nuevalista.addAll(client.get().getDireccionesAdicionales());
            nuevalista.add(direccion);
            client.get().setDireccionesAdicionales(nuevalista);
            this.repository.save(client.get());
            return ResponseEntity.ok("se registro una nueva direccion para el cliente con el id:" + client.get().getId());
        }
        return ResponseEntity.badRequest().body("no existe un cliente con el id: " + id);
    }

    @Override
    public List<Direccion> obtenerDirecciones(Long id) {
        Optional<Cliente> client = this.repository.findById(id);
        if(client.isPresent()){
            List<Direccion> encontradas = new ArrayList<>();
            encontradas.add(client.get().getDireccionMatriz());
            encontradas.addAll(client.get().getDireccionesAdicionales());
            return encontradas;
        }
        throw new IllegalArgumentException("no existe un cliente con el id: " + id);
    }
}
