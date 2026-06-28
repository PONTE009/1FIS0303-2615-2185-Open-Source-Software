package upc.service.taller.alumno.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import upc.service.taller.alumno.utils.Auditoria;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_personas")
public class PersonaEntity extends Auditoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona")
    private Long idPersona;

    @NotBlank
    @Column(name = "nombres", length = 150, nullable = false)
    private String nombres;

    private String apellidos;
    private String correo;
    private String telefono;

    @Column(length = 8, unique = true)
    private String documento;

    @JsonIgnore
    @OneToOne(mappedBy = "persona", fetch = FetchType.EAGER)
    private UsuarioEntity usuario;
}
