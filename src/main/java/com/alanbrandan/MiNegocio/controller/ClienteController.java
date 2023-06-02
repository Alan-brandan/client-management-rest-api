package com.alanbrandan.MiNegocio.controller;

import com.alanbrandan.MiNegocio.domain.Cliente;
import com.alanbrandan.MiNegocio.services.interfaces.ClienteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService service;
    @GetMapping("/")
    public ResponseEntity<?> getClientesFiltrados(@RequestParam(required = false) String nombre,
                                              @RequestParam(required = false, name = "id") String numeroIdentificacion){
        return this.service.getClientesFiltrados(nombre, numeroIdentificacion);
    }
    @PostMapping("/")
    public ResponseEntity<?> nuevoCliente(@RequestBody Cliente cliente){
        return this.service.nuevoCliente(cliente);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> modificarCliente(@PathVariable Long id,
                                    @RequestBody Cliente cliente){
        return this.service.modificarCliente(id,cliente);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPorId(@PathVariable Long id){
        return this.service.eliminarCliente(id);
    }
}
