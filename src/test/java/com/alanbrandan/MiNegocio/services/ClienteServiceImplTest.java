package com.alanbrandan.MiNegocio.services;

import com.alanbrandan.MiNegocio.domain.Cliente;
import com.alanbrandan.MiNegocio.domain.Direccion;
import com.alanbrandan.MiNegocio.repository.ClienteRepository;
import com.alanbrandan.MiNegocio.services.interfaces.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class ClienteServiceImplTest {

    @Autowired
    private ClienteService clienteService;
    @MockBean
    private ClienteRepository clienteRepository;

    private Cliente cliente1;
    private Cliente cliente2;
    private Cliente cliente3;
    private final List<Cliente> listaclientes = new ArrayList<>();

    @BeforeEach
    public void SetUp(){
        cliente1 = new Cliente();
        cliente1.setId(4L);
        cliente1.setNombres("John Doe");
        cliente1.setCorreo("jDoe@test.com");
        cliente1.setNumeroCelular(12345678);
        cliente1.setTipoIdentificacion("RUC");
        cliente1.setNumeroIdentificacion("12345678");
        cliente1.setDireccionMatriz(new Direccion(6L,"tucuman","buenos aires","calle1"));
        cliente1.setDireccionesAdicionales(Arrays.asList(new Direccion(8L,"buenos aires","buenos aires","calle45")
                ,new Direccion(3L,"chubut","buenos aires","calle12")));

        cliente2 = new Cliente();
        cliente2.setId(6L);
        cliente2.setNombres("Jane Doe");
        cliente2.setCorreo("janeDoe@test.com");
        cliente2.setNumeroCelular(1233458);
        cliente2.setTipoIdentificacion("CEDULA");
        cliente2.setNumeroIdentificacion("1223478");
        cliente2.setDireccionMatriz(new Direccion(12L,"buenos aires","buenos aires","calle68"));
        cliente2.setDireccionesAdicionales(Arrays.asList(new Direccion(7L,"misiones","buenos aires","calle 5")
                ,new Direccion(21L,"jujuy","buenos aires","calle 120")));

        cliente3 = new Cliente();
        cliente3.setId(9L);
        cliente3.setNombres("batman");
        cliente3.setCorreo("bbb@test.com");
        cliente3.setNumeroCelular(9833458);
        cliente3.setTipoIdentificacion("CEDULA");
        cliente3.setNumeroIdentificacion("1223588");
        cliente3.setDireccionMatriz(new Direccion(67L,"Gotham","US","calle 628"));
        cliente3.setDireccionesAdicionales(List.of(new Direccion(95L, "buenos aires", "buenos aires", "calle 66")));

        listaclientes.add(cliente1);
        listaclientes.add(cliente2);
    }

    @Test
    void obtenerClientesFiltrados_NoParams() {
        when(clienteRepository.getClientesFiltrados(any(), any())).thenReturn(listaclientes);
        ResponseEntity<?> encontrados = clienteService.getClientesFiltrados(null, null);
        assertNotNull(encontrados);
        assertEquals(2,((List<Cliente>)encontrados.getBody()).size());
    }

    @Test
    void obtenerClientesFiltrados_PassingValidID() {
        when(clienteRepository.getClientesFiltrados(any(),anyString())).thenReturn(new ArrayList<>(Arrays.asList(cliente2)));
        ResponseEntity<?> encontrado = clienteService.getClientesFiltrados(null,"1223478");
        assertEquals("1223478",((List<Cliente>)encontrado.getBody()).get(0).getNumeroIdentificacion());
    }
    @Test
    void obtenerClientesFiltrados_PassingValidName() {
        List<Cliente> nueva = new ArrayList<>(Collections.singletonList(cliente2));
        when(clienteRepository.getClientesFiltrados(anyString(),any())).thenReturn(nueva);
        ResponseEntity<?> encontrado = clienteService.getClientesFiltrados("Jane Doe",null);
        assertEquals("1223478",((List<Cliente>)encontrado.getBody()).get(0).getNumeroIdentificacion() );
    }
    @Test
    void obtenerClientesFiltrados_PassingBothNameAndId() {
        List<Cliente> nueva = new ArrayList<>(Collections.singletonList(cliente2));
        when(clienteRepository.getClientesFiltrados(anyString(),anyString())).thenReturn(nueva);

        ResponseEntity<?> results = clienteService.getClientesFiltrados(cliente2.getNombres(),cliente2.getNumeroIdentificacion());

        assertEquals("Jane Doe", ((List<Cliente>)results.getBody()).get(0).getNombres());
        assertEquals("1223478", ((List<Cliente>)results.getBody()).get(0).getNumeroIdentificacion());

    }

    @Test
    void nuevoCliente_WithValidID() {
        Cliente nuevo = new Cliente();
        nuevo.setNombres("pedro");
        nuevo.setId(96L);
        nuevo.setTipoIdentificacion("CEDULA");
        nuevo.setNumeroIdentificacion("1299078");
        nuevo.setDireccionMatriz(new Direccion(12L,"buenos aires","buenos aires","calle68"));

        when(clienteRepository.findAll()).thenReturn(listaclientes);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(nuevo);

        Cliente result =(Cliente)clienteService.nuevoCliente(new Cliente()).getBody();
        assertEquals(nuevo,result);
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void nuevoCliente_WithExistingID() {

        Cliente nuevo = new Cliente();
        nuevo.setNombres("pedro");
        nuevo.setId(96L);
        nuevo.setNumeroIdentificacion("1223478");
        when(clienteRepository.getClientesFiltrados(any(),any())).thenReturn(listaclientes);

        ResponseEntity<?> results = clienteService.nuevoCliente(nuevo);
        assertEquals(HttpStatus.BAD_REQUEST,results.getStatusCode());
        assertEquals("ya existe un cliente con el numero de identificacion ingresado", results.getBody());
    }

    @Test
    void modificarCliente_WithValidID() {
        when(clienteRepository.getClientesFiltrados(any(),any())).thenReturn(listaclientes);
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente1));
        Cliente nuevo = new Cliente();
        nuevo.setNombres(cliente1.getNombres());
        nuevo.setTipoIdentificacion("CEDULA");
        nuevo.setNumeroIdentificacion("1299078");
        nuevo.setDireccionMatriz(new Direccion(12L,"buenos aires","buenos aires","calle68"));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(nuevo);

        clienteService.modificarCliente(cliente1.getId(),cliente1);

        assertEquals(nuevo.getNombres(),cliente1.getNombres());
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void modificarCliente_WithExistingIDN() {

        when(clienteRepository.getClientesFiltrados(any(),any())).thenReturn(listaclientes);
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente1));
        Cliente nuevo = new Cliente();
        nuevo.setNombres(cliente1.getNombres());
        nuevo.setTipoIdentificacion("CEDULA");
        nuevo.setNumeroIdentificacion(cliente2.getNumeroIdentificacion());
        nuevo.setDireccionMatriz(new Direccion(12L,"buenos aires","buenos aires","calle68"));

        ResponseEntity<?> results = clienteService.modificarCliente(cliente1.getId(),nuevo);
        assertEquals("ya existe un cliente con el numero de identificacion ingresado", results.getBody());
    }

    @Test
    void modificarCliente_PassingInvalidID() {
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.empty());

        Cliente nuevo = new Cliente();
        nuevo.setNombres("pedro");
        nuevo.setNumeroIdentificacion("99999");

        ResponseEntity<?> results = clienteService.modificarCliente(nuevo.getId(),nuevo);
        assertEquals("no existe un cliente con el ID ingresado", results.getBody());
    }

    @Test
    void eliminarCliente_WithValidID() {
        when(clienteRepository.findById(any())).thenReturn(Optional.ofNullable(cliente1));
        clienteService.eliminarCliente(cliente1.getId());
        verify(clienteRepository, times(1)).deleteById(cliente1.getId());
    }
    @Test
    void eliminarCliente_WithInvalidID() {
        when(clienteRepository.findById(any())).thenReturn(Optional.empty());
        clienteService.eliminarCliente(cliente3.getId());
        ResponseEntity<?> response= clienteService.eliminarCliente(cliente3.getId());
        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertEquals("no existe un cliente con el ID ingresado",response.getBody());
    }
}