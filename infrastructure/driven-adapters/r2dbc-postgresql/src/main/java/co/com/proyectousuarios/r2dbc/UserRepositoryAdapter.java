package co.com.proyectousuarios.r2dbc;

import co.com.proyectousuarios.model.user.User;
import co.com.proyectousuarios.r2dbc.config.TransactionConfig;
import co.com.proyectousuarios.r2dbc.entity.UserEntity;
import co.com.proyectousuarios.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class UserRepositoryAdapter extends ReactiveAdapterOperations<
    User, UserEntity, Long, UserRepository> implements co.com.proyectousuarios.model.user.gateways.UserRepository
{
    private final TransactionalOperator transactionalOperator;

    public UserRepositoryAdapter(UserRepository repository, ObjectMapper mapper, TransactionalOperator transactionalOperator) {
        super(repository, mapper, d -> mapper.map(d, User.class));
        this.transactionalOperator = transactionalOperator;
    }

    @Override
    public Mono<User> saveUser(User user) {
        UserEntity entity = toData(user);

        return transactionalOperator
                .transactional(repository.save(entity))
                .map(savedEntity -> new User(
                        savedEntity.getIdUser(),
                        savedEntity.getName(),
                        savedEntity.getLastName(),
                        savedEntity.getEmail(),
                        savedEntity.getPassword(),
                        savedEntity.getDocumentUser(),
                        savedEntity.getBaseSalary(),
                        savedEntity.getFechaNacimiento(),
                        savedEntity.getDireccion(),
                        savedEntity.getTelefono(),
                        savedEntity.getIdRole()
                ));
    }

    @Override
    public Flux<User> listUsers() {
        return repository.findAll()
                .map(userEntity -> mapper.map(userEntity, User.class));
    }

    @Override
    public Mono<User> findUserByEmail(String email) {
        return repository.findByEmail(email)
                .map(userEntity -> mapper.map(userEntity, User.class));
    }

    @Override
    public Mono<User> findUserById(Long idUser) {
        return repository.findById(idUser)
                .map(userEntity -> mapper.map(userEntity, User.class));
    }

    @Override
    public Mono<User> editUser(User user) {
        UserEntity entity = toData(user);

        return transactionalOperator
                .transactional(repository.save(entity))
                .map(updatedEntity -> new User(
                        updatedEntity.getIdUser(),
                        updatedEntity.getName(),
                        updatedEntity.getLastName(),
                        updatedEntity.getEmail(),
                        updatedEntity.getPassword(),
                        updatedEntity.getDocumentUser(),
                        updatedEntity.getBaseSalary(),
                        updatedEntity.getFechaNacimiento(),
                        updatedEntity.getDireccion(),
                        updatedEntity.getTelefono(),
                        updatedEntity.getIdRole()
                ));
    }

}
