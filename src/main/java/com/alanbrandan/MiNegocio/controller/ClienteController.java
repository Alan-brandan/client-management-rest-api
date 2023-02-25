package com.alanbrandan.MiNegocio.controller;

import com.alanbrandan.MiNegocio.domain.Cliente;
import com.alanbrandan.MiNegocio.services.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService service;
    public ClienteController(ClienteService service) { this.service=service; }

    @GetMapping
    public List<Cliente> obtenerClientes(){
        return this.service.obtenerClientes();
    }

    @GetMapping("/{nameOrID}")
    public List<Cliente> obtenerClientesConParam(@PathVariable(name = "nameOrID") String nombre,@PathVariable(name = "nameOrID") String id){
        return this.service.obtenerClientesConParam(nombre, id);

    }
    @PostMapping
    public Cliente nuevoCliente(@RequestBody Cliente cliente){
        return this.service.nuevoCliente(cliente);
    }
    @PutMapping
    public Cliente modificarCliente(@RequestBody Cliente cliente){
        return this.service.modificarCliente(cliente);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCliente(@PathVariable Long id){
        return this.service.eliminarCliente(id);
    }
}
