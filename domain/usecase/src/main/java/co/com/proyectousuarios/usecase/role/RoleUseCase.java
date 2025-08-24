package co.com.proyectousuarios.usecase.role;

import co.com.proyectousuarios.model.role.Role;
import co.com.proyectousuarios.model.role.gateways.RoleRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RoleUseCase implements IRoleUseCase {
    private final RoleRepository roleRepository;

    @Override
    public Mono<Role> saveRole(Role role) {
        if (role == null) {
            return Mono.error(new RuntimeException("El rol no puede ser nulo"));
        }
        if (role.getName() == null || role.getName().isBlank()) {
            return Mono.error(new RuntimeException("El nombre del rol es obligatorio"));
        }

        return roleRepository.saveRole(role)
                .switchIfEmpty(Mono.error(new RuntimeException("No se pudo guardar el rol")));
    }

    @Override
    public Flux<Role> listRoles() {
        return roleRepository.listRoles()
                .switchIfEmpty(Flux.error(new RuntimeException("No se encontraron roles")));
    }


}
