package com.alanbrandan.MiNegocio.controller;

import com.alanbrandan.MiNegocio.domain.Direccion;
import com.alanbrandan.MiNegocio.services.DireccionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/direcciones")
public class DireccionController {

    private final DireccionService service;
    public DireccionController(DireccionService service) { this.service=service; }

    @PostMapping("/{id}")
    public ResponseEntity<?> crearDireccion(@PathVariable Long id,@RequestBody Direccion direccion){
        return ResponseEntity.ok(this.service.crearDireccion(id,direccion));
    }
    @GetMapping("/{id}")
    public List<Direccion> obtenerDirecciones(@PathVariable Long id){
        return this.service.obtenerDirecciones(id);
    }
}
