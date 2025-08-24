package co.com.proyectousuarios.api.mapper;

import co.com.proyectousuarios.api.dto.user.EditUserDto;
import co.com.proyectousuarios.api.dto.user.SaveUserDto;
import co.com.proyectousuarios.api.dto.user.UserDto;
import co.com.proyectousuarios.model.user.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    UserDto toResponse(User user);
    List<UserDto> toResponseList(List<User> users);
    User toModel(SaveUserDto saveUserDto);
    User toModel(EditUserDto editUserDto);
}
