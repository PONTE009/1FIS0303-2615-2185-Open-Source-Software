package upc.service.taller.alumno.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_proveedores")
public class ProveedoresEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proveedor")
    private Long idProveedor;

    @NotBlank
    @Column(name = "razon_social", nullable = false, length = 150)
    private String razonSocial;

    @Column(length = 20, unique = true)
    private String ruc;

    @Column(length = 120)
    private String correo;

    @Column(length = 9)
    private String telefono;

    @Column(length = 250)
    private String direccion;

    @JsonIgnore
    @OneToMany(mappedBy = "proveedor", fetch = FetchType.LAZY)
    private List<ProductoProveedorEntity> productosProveedores = new ArrayList<>();
}
