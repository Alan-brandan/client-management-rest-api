package com.alanbrandan.MiNegocio.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tipoIdentificacion;
    private String numeroIdentificacion;
    private String nombres;
    private String correo;
    private int numeroCelular;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Direccion direccionMatriz;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "direcciones_id"
    )
    private List<Direccion> direccionesAdicionales;

    public Cliente() {
    }
    public Cliente(Long id,String tipoIdentificacion,String numeroIdentificacion,String nombres,String correo,int numeroCelular,Direccion direccionMatriz,List<Direccion> direccionesAdicionales) {
        this.id=id;
        this.tipoIdentificacion=tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.nombres=nombres;
        this.correo=correo;
        this.numeroCelular=numeroCelular;
        this.direccionMatriz=direccionMatriz;
        this.direccionesAdicionales=direccionesAdicionales;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getNumeroCelular() {
        return numeroCelular;
    }

    public void setNumeroCelular(int numeroCelular) {
        this.numeroCelular = numeroCelular;
    }

    public Direccion getDireccionMatriz() {
        return direccionMatriz;
    }

    public void setDireccionMatriz(Direccion direccionMatriz) {
        this.direccionMatriz = direccionMatriz;
    }

    public List<Direccion> getDireccionesAdicionales() {
        return direccionesAdicionales;
    }

    public void setDireccionesAdicionales(List<Direccion> direccionesAdicionales) {
        this.direccionesAdicionales = direccionesAdicionales;
    }
}
