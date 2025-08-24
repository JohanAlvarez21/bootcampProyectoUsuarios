package co.com.proyectousuarios.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("users")
public class UserEntity {

    @Id
    private Long idUser;

    private String name;

    private String lastName;

    private String email;

    private String password;

    private Integer documentUser;

    private Double baseSalary;

    private LocalDate fechaNacimiento;

    private String direccion;

    private String telefono;

    private Long idRole;
}
