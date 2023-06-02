package com.alanbrandan.MiNegocio.services.implementation;
import com.alanbrandan.MiNegocio.domain.Cliente;
import com.alanbrandan.MiNegocio.domain.Direccion;
import com.alanbrandan.MiNegocio.repository.ClienteRepository;
import com.alanbrandan.MiNegocio.services.interfaces.DireccionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@Service
public class DireccionServiceImpl implements DireccionService {

    private final ClienteRepository repository;

    @Override
    public ResponseEntity<?> crearDireccion(Long id,Direccion direccion) {
        Optional<Cliente> client = this.repository.findById(id);
        if(client.isPresent()){
            List<Direccion> nuevalista = new ArrayList<>(client.get().getDireccionesAdicionales());
            nuevalista.add(direccion);
            client.get().setDireccionesAdicionales(nuevalista);
            this.repository.save(client.get());
            return ResponseEntity.ok("se registro una nueva direccion para el cliente con el id: " + id);
        }
        return ResponseEntity.badRequest().body("no existe un cliente con el id: " + id);
    }

    @Override
    public ResponseEntity<?> obtenerDirecciones(Long id) {
        Optional<Cliente> client = this.repository.findById(id);
        if(client.isPresent()){
            List<Direccion> encontradas = new ArrayList<>(Arrays.asList(client.get().getDireccionMatriz()));
            encontradas.addAll(client.get().getDireccionesAdicionales());
            return ResponseEntity.ok(encontradas);
        }
        return ResponseEntity.badRequest().body("no existe un cliente con el id: " + id);
    }
}
