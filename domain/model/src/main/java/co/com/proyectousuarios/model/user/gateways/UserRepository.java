package co.com.proyectousuarios.model.user.gateways;
import co.com.proyectousuarios.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<User> saveUser(User user);
    Flux<User> listUsers();
    Mono<User> findUserByEmail(String email);
    Mono<User> findUserById(Long idUser);
    Mono<User> editUser(User user);
}
