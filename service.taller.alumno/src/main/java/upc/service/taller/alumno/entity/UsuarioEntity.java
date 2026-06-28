package upc.service.taller.alumno.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import upc.service.taller.alumno.utils.Auditoria;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_usuario")
public class UsuarioEntity extends Auditoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;
    private String UserName;
    private String Password;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_persona", nullable = false)
    private PersonaEntity persona;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario",fetch = FetchType.LAZY)
    private List<PedidoEntity> pedidos = new ArrayList<>();
}
