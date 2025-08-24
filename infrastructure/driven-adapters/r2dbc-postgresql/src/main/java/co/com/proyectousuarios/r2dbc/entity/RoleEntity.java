package co.com.proyectousuarios.r2dbc.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("roles") // nombre exacto de la tabla en PostgreSQL
public class RoleEntity {

    @Id // de Spring Data
    private Long idRole;

    private String name;

    private String description;
}