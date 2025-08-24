package co.com.proyectousuarios.usecase.role;

import co.com.proyectousuarios.model.role.Role;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IRoleUseCase {
    Mono<Role> saveRole(Role role);
    Flux<Role> listRoles();
}
