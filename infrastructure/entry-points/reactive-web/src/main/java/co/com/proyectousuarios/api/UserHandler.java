package co.com.proyectousuarios.api;

import co.com.proyectousuarios.api.dto.user.EditUserDto;
import co.com.proyectousuarios.api.dto.user.SaveUserDto;
import co.com.proyectousuarios.api.dto.user.UserDto;
import co.com.proyectousuarios.api.mapper.UserDtoMapper;
import co.com.proyectousuarios.model.user.User;
import co.com.proyectousuarios.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserHandler {

    private final UserUseCase userUseCase;
    private final UserDtoMapper userDtoMapper;

    public Mono<ServerResponse> listUsers(ServerRequest serverRequest) {
        log.info("Listando usuarios");
        return userUseCase.listUsers()
                .collectList()
                .map(userDtoMapper::toResponseList)
                .doOnError(e -> log.error("Error en listUsers: {}", e.getMessage()))
                .flatMap(userDtos ->
                        ServerResponse.ok()
                                .bodyValue(userDtos)
                )
                .onErrorResume(e ->
                        ServerResponse.badRequest()
                                .bodyValue(Map.of("error", e.getMessage()))
                );
    }


    public Mono<ServerResponse> listenGETOtherUseCase(ServerRequest serverRequest) {
        // useCase2.logic();
        return ServerResponse.ok().bodyValue("");
    }

    public Mono<ServerResponse> saveUser(ServerRequest serverRequest) {
        log.info("Guardando usuario");

        return serverRequest.bodyToMono(SaveUserDto.class)
                .flatMap(userRequest -> {
                    User user = new User(
                            null,
                            userRequest.name(),
                            userRequest.lastName(),
                            userRequest.email(),
                            userRequest.password(),
                            userRequest.documentUser(),
                            userRequest.baseSalary(),
                            userRequest.fechaNacimiento(),
                            userRequest.direccion(),
                            userRequest.telefono(),
                            userRequest.idRole()
                    );
                    return userUseCase.saveUser(user);
                })
                .doOnError(e -> log.error("Error en saveUser: {}", e.getMessage())) // log local
                .map(userDtoMapper::toResponse)
                .flatMap(userResponse ->
                        ServerResponse.ok()
                                .bodyValue(userResponse)
                );
    }




    public Mono<ServerResponse> editUser(ServerRequest serverRequest) {
        log.info("Editando usuario");
        Long idUser = Long.valueOf(serverRequest.pathVariable("idUser"));
        return serverRequest.bodyToMono(EditUserDto.class)
                .flatMap(userRequest -> {
                    User user = new User(
                            idUser,
                            userRequest.name(),
                            userRequest.lastName(),
                            userRequest.email(),
                            userRequest.password(),
                            userRequest.documentUser(),
                            userRequest.baseSalary(),
                            userRequest.fechaNacimiento(),
                            userRequest.direccion(),
                            userRequest.telefono(),
                            userRequest.idRole()
                    );
                    return userUseCase.editUser(user);
                })
                .doOnError(e -> log.error("Error en editUser: {}", e.getMessage()))
                .map(userDtoMapper::toResponse)
                .flatMap(userResponse ->
                        ServerResponse.ok()
                                .bodyValue(userResponse)
                );
    }


}
