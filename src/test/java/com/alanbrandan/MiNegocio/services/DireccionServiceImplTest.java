package com.alanbrandan.MiNegocio.services;

import com.alanbrandan.MiNegocio.domain.Cliente;
import com.alanbrandan.MiNegocio.domain.Direccion;
import com.alanbrandan.MiNegocio.repository.ClienteRepository;
import com.alanbrandan.MiNegocio.services.interfaces.DireccionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class DireccionServiceImplTest {

    @Autowired
    private DireccionService direccionService;
    @MockBean
    private ClienteRepository clienteRepository;

    private Cliente cliente1;

    @BeforeEach
    public void SetUp() {
        cliente1 = new Cliente();
        cliente1.setId(4L);
        cliente1.setNombres("John Doe");
        cliente1.setCorreo("jDoe@test.com");
        cliente1.setNumeroCelular(12345678);
        cliente1.setTipoIdentificacion("RUC");
        cliente1.setNumeroIdentificacion("12345678");
        cliente1.setDireccionMatriz(new Direccion(6L, "tucuman", "buenos aires", "calle1"));
        cliente1.setDireccionesAdicionales(Arrays.asList(new Direccion(8L, "buenos aires", "buenos aires", "calle45")
                , new Direccion(3L, "chubut", "buenos aires", "calle12")));
    }

    @Test
    void crearDireccion_PassingValidID() {
        Direccion nueva = new Direccion(643L, "chubut", "buenos aires", "calle31");

        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente1));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente1);

        ResponseEntity<?> result = direccionService.crearDireccion(cliente1.getId(),nueva);

        assertEquals(HttpStatus.OK,result.getStatusCode());
        assertEquals("se registro una nueva direccion para el cliente con el id: " + cliente1.getId(),result.getBody());

    }

    @Test
    void crearDireccion_PassingInvalidID() {
        Direccion nueva = new Direccion(90L,"buenos aires","chubut","calle869");
        Long idTest = 90L;

        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente1);
        ResponseEntity<?> result = direccionService.crearDireccion(idTest,new Direccion());

        assertEquals(HttpStatus.BAD_REQUEST,result.getStatusCode());
        assertEquals("no existe un cliente con el id: " + idTest,result.getBody());

    }

    @Test
    void obtenerDirecciones_PassingValidID() {
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente1));
        ResponseEntity<?> result = direccionService.obtenerDirecciones(cliente1.getId());

        //assertEquals(HttpStatus.OK,result.getStatusCode());
        assertEquals(3,((List<Direccion>)result.getBody()).size());
    }

    @Test
    void obtenerDirecciones_PassingInvalidID() {
        ResponseEntity<?> result = direccionService.obtenerDirecciones(cliente1.getId());

        assertEquals(HttpStatus.BAD_REQUEST,result.getStatusCode());
        assertEquals("no existe un cliente con el id: " + cliente1.getId(),result.getBody());
    }
}