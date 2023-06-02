package com.alanbrandan.MiNegocio.domain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20)
    private String tipoIdentificacion;
    @Column(length = 15)
    private String numeroIdentificacion;
    @Column(length = 20)
    private String nombres;
    @Column(length = 30,nullable = false)
    private String correo;
    @Column(length = 15)
    private int numeroCelular;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Direccion direccionMatriz;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "direcciones_id"
    )
    private List<Direccion> direccionesAdicionales;
}
