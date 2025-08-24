package co.com.proyectousuarios.api.dto.user;

import java.time.LocalDate;

public record EditUserDto(
        String name,
        String lastName,
        String email,
        Integer documentUser,
        String password,
        Double baseSalary,
        LocalDate fechaNacimiento,
        String direccion,
        String telefono,
        Long idRole
) {
}
