package co.com.proyectousuarios.api.mapper;

import co.com.proyectousuarios.api.dto.role.EditRoleDto;
import co.com.proyectousuarios.api.dto.role.SaveRoleDto;
import co.com.proyectousuarios.api.dto.role.RoleDto;
import co.com.proyectousuarios.model.role.Role;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleDtoMapper {

    RoleDto toResponse(Role role);
    List<RoleDto> toResponseList(List<Role> roles);
    Role toModel(SaveRoleDto saveRoleDto);
    Role toModel(EditRoleDto editRoleDto);
}
