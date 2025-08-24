package co.com.proyectousuarios.api.dto.user;

import co.com.proyectousuarios.model.role.Role;

import java.time.LocalDate;

public record UserDto(
        Long idUser,
        String name,
        String lastName,
        String email,
        String password,
        Integer documentUser,
        Double baseSalary,
        LocalDate fechaNacimiento,
        String direccion,
        String telefono,
        Long idRole
) {
}
