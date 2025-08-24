package co.com.proyectousuarios.api;

import co.com.proyectousuarios.api.dto.role.RoleDto;
import co.com.proyectousuarios.api.mapper.RoleDtoMapper;
import co.com.proyectousuarios.api.mapper.UserDtoMapper;
import co.com.proyectousuarios.model.role.Role;
import co.com.proyectousuarios.usecase.role.RoleUseCase;
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
public class RoleHandler {

    private final RoleUseCase roleUseCase;
    private final RoleDtoMapper roleDtoMapper;


    public Mono<ServerResponse> listRoles(ServerRequest serverRequest) {
        log.info("Listando roles");
        return roleUseCase.listRoles()
                .collectList()
                .map(roleDtoMapper::toResponseList)
                .doOnError(e -> log.error("Error en listRoles: {}", e.getMessage()))
                .flatMap(roleDtos ->
                        ServerResponse.ok()
                                .bodyValue(roleDtos)
                )
                .onErrorResume(e ->
                        ServerResponse.badRequest()
                                .bodyValue(Map.of("error", e.getMessage()))
                );
    }

    public Mono<ServerResponse> saveRole(ServerRequest serverRequest) {
        log.info("Guardando rol");
        return serverRequest.bodyToMono(RoleDto.class)
                .flatMap(roleRequest -> {
                    Role role = new Role(
                            null,
                            roleRequest.name(),
                            roleRequest.description()
                    );
                    return roleUseCase.saveRole(role);
                })
                .doOnError(e -> log.error("Error en saveRole: {}", e.getMessage()))
                .map(roleDtoMapper::toResponse)
                .flatMap(roleResponse ->
                        ServerResponse.ok()
                                .bodyValue(roleResponse)
                )
                .onErrorResume(e ->
                        ServerResponse.badRequest()
                                .bodyValue(Map.of("error", e.getMessage()))
                );
    }

}
