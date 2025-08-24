package co.com.proyectousuarios.r2dbc;

import co.com.proyectousuarios.model.role.Role;
import co.com.proyectousuarios.r2dbc.entity.RoleEntity;
import co.com.proyectousuarios.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class RoleRepositoryAdapter extends ReactiveAdapterOperations<
        Role, RoleEntity, Long, RoleRepository> implements co.com.proyectousuarios.model.role.gateways.RoleRepository {

    private final TransactionalOperator transactionalOperator;

    public RoleRepositoryAdapter(RoleRepository repository, ObjectMapper mapper, TransactionalOperator transactionalOperator) {
        super(repository, mapper, d -> mapper.map(d, Role.class/* change for domain model */));
        this.transactionalOperator = transactionalOperator;
    }

    @Override
    public Mono<Role> saveRole(Role role) {
        RoleEntity entity = toData(role);
        return transactionalOperator.transactional(repository.save(entity))
                .map(savedRole -> new Role(
                        savedRole.getIdRole(),
                        savedRole.getName(),
                        savedRole.getDescription()
                ));
    }

    @Override
    public Flux<Role> listRoles() {
        return repository.findAll()
                .map(roleEntity -> new Role(
                        roleEntity.getIdRole(),
                        roleEntity.getName(),
                        roleEntity.getDescription()
                ));
    }

    @Override
    public Mono<Role> editRole(Role role) {
        return null;
    }

    @Override
    public Mono<Void> deleteRole(Long idRole) {
        return null;
    }
}
