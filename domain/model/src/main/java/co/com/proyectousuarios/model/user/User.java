package co.com.proyectousuarios.model.user;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class User {

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
