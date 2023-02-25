package com.alanbrandan.MiNegocio.services;

import com.alanbrandan.MiNegocio.domain.Cliente;
import com.alanbrandan.MiNegocio.domain.Direccion;
import com.alanbrandan.MiNegocio.repository.ClienteRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

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
    void obtenerClientes() {
        when(clienteRepository.findAll()).thenReturn(listaclientes);
        List<Cliente> encontrados = clienteService.obtenerClientes();
        assertNotNull(encontrados);
        assertEquals(2,encontrados.size());
    }

    @Test
    void obtenerClientesConParam_PassingValidID() {
        when(clienteRepository.findByNumeroIdentificacionLikeIgnoreCase(anyString())).thenReturn(cliente2);
        List<Cliente> encontrado = clienteService.obtenerClientesConParam("","1223478");
        assertEquals("1223478",encontrado.get(0).getNumeroIdentificacion());
    }
    @Test
    void obtenerClientesConParam_PassingValidName() {
        List<Cliente> nueva = new ArrayList<>(Collections.singletonList(cliente2));
        when(clienteRepository.findByNombresLikeIgnoreCase(anyString())).thenReturn(nueva);
        List<Cliente> encontrado = clienteService.obtenerClientesConParam("Jane Doe","");
        assertTrue(encontrado.contains(cliente2));
    }
    @Test
    void obtenerClientesConParam_PassingNoParam() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                clienteService.obtenerClientesConParam("","")
        );
        assertEquals("no se encontro clientes con ese nombre/numero de identificacion", exception.getMessage());
    }
    @Test
    void obtenerClientesConParam_PassingBothNameAndId() {
        when(clienteRepository.findByNumeroIdentificacionLikeIgnoreCase(anyString())).thenReturn(cliente2);
        List<Cliente> nueva = new ArrayList<>(Collections.singletonList(cliente2));
        when(clienteRepository.findByNombresLikeIgnoreCase(anyString())).thenReturn(nueva);

        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                clienteService.obtenerClientesConParam("Jane Doe","433434")
        );
        assertEquals("solo se permite ingresar nombre o numero de identificacion como parametro, no ambos al mismo tiempo", exception.getMessage());

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

        Cliente result =clienteService.nuevoCliente(new Cliente());
        assertEquals(nuevo,result);
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void nuevoCliente_WithExistingID() {

        Cliente nuevo = new Cliente();
        nuevo.setNombres("pedro");
        nuevo.setId(96L);
        nuevo.setTipoIdentificacion("CEDULA");
        nuevo.setNumeroIdentificacion("1223478");
        nuevo.setDireccionMatriz(new Direccion(12L,"buenos aires","buenos aires","calle68"));

        when(clienteRepository.findAll()).thenReturn(listaclientes);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(nuevo);

        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                clienteService.nuevoCliente(nuevo)
                );
        assertEquals("ya existe un cliente con ese numero de identificación", exception.getMessage());
    }

    @Test
    void modificarCliente_WithValidID() {
        when(clienteRepository.findAll()).thenReturn(listaclientes);
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente1));
        String testname = "juan";

        cliente1.setNombres(testname);
        clienteService.modificarCliente(cliente1);

        assertEquals(testname,cliente1.getNombres());

    }

    @Test
    void modificarCliente_WithExistingIDN() {
        when(clienteRepository.findAll()).thenReturn(listaclientes);
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente1));
        when(clienteRepository.findByNumeroIdentificacionLikeIgnoreCase(anyString())).thenReturn(cliente2);

        Cliente nuevo = new Cliente();
        nuevo.setNombres("pedro");
        nuevo.setId(96L);
        nuevo.setTipoIdentificacion("CEDULA");
        nuevo.setNumeroIdentificacion("90900");
        nuevo.setDireccionMatriz(new Direccion(12L,"buenos aires","buenos aires","calle68"));

        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                clienteService.modificarCliente(nuevo)
        );
        assertEquals("ya existe un cliente con el numero de identificacion ingresado", exception.getMessage());

    }

    @Test
    void modificarCliente_PassingInvalidID() {
        when(clienteRepository.findAll()).thenReturn(listaclientes);

        Cliente nuevo = new Cliente();
        nuevo.setNombres("pedro");
        nuevo.setId(288L);
        nuevo.setTipoIdentificacion("CEDULA");
        nuevo.setNumeroIdentificacion("99999");
        nuevo.setDireccionMatriz(new Direccion(12L,"buenos aires","buenos aires","calle68"));

        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                clienteService.modificarCliente(nuevo)
        );
        assertEquals("no existe un cliente con el ID ingresado", exception.getMessage());

    }

    @Test
    void eliminarCliente_WithValidID() {
        when(clienteRepository.findAll()).thenReturn(listaclientes);
        clienteService.eliminarCliente(cliente1.getId());
        verify(clienteRepository, times(1)).deleteById(cliente1.getId());
    }
    @Test
    void eliminarCliente_WithInvalidID() {
        when(clienteRepository.findAll()).thenReturn(listaclientes);
        clienteService.eliminarCliente(cliente3.getId());
        var response = clienteService.eliminarCliente(cliente3.getId());
        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertEquals("no existe un cliente con el ID ingresado",response.getBody());
    }
}