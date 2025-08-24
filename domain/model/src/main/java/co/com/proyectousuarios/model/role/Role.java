package co.com.proyectousuarios.model.role;
import lombok.*;
//import lombok.NoArgsConstructor;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Role {
    private Long idRole;
    private String name;
    private String description;
}
