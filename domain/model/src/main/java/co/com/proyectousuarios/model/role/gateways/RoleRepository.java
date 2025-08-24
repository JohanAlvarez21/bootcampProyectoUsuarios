package co.com.proyectousuarios.model.role.gateways;

import co.com.proyectousuarios.model.role.Role;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RoleRepository {
    Mono<Role> saveRole(Role role);
    Flux<Role> listRoles();
    Mono<Role> editRole(Role role);
    Mono<Void> deleteRole(Long idRole);
}
