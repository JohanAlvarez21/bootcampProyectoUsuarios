package co.com.proyectousuarios.usecase.user;

import co.com.proyectousuarios.model.user.User;
import co.com.proyectousuarios.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase implements IUserUseCase {
    private final UserRepository userRepository;

    public Mono<User> saveUser(User user) {

        if (user.getName() == null || user.getName().isBlank() ||
                user.getLastName() == null || user.getLastName().isBlank() ||
                user.getEmail() == null || user.getEmail().isBlank() ||
                user.getPassword() == null || user.getPassword().isBlank() ||
                user.getDocumentUser() == null || user.getBaseSalary() == null ||
                user.getFechaNacimiento() == null || user.getDireccion() == null ||
                user.getDireccion().isBlank() || user.getTelefono() == null ||
                user.getTelefono().isBlank() || user.getIdRole() == null) {
            return Mono.error(new RuntimeException("Campos obligatorios faltantes"));
        }
        if (!user.getEmail().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}")) {
            return Mono.error(new RuntimeException("Email inválido"));
        }
        if (user.getBaseSalary() < 0 || user.getBaseSalary() > 15_000_000) {
            return Mono.error(new RuntimeException("Salario fuera de rango"));
        }
        return userRepository.findUserByEmail(user.getEmail())
                .flatMap(existingUser -> Mono.<User>error(new RuntimeException("El email ya está registrado en otro usuario")))
                .switchIfEmpty(userRepository.saveUser(user));
    }

    public Flux<User> listUsers() {
        return userRepository.listUsers()
                .map(userFound -> new User(
                        userFound.getIdUser(),
                        userFound.getName(),
                        userFound.getLastName(),
                        userFound.getEmail(),
                        userFound.getPassword(),
                        userFound.getDocumentUser(),
                        userFound.getBaseSalary(),
                        userFound.getFechaNacimiento(),
                        userFound.getDireccion(),
                        userFound.getTelefono(),
                        userFound.getIdRole()
                ))
                .switchIfEmpty(Mono.error(new RuntimeException("No se encontraron usuarios")));
    }

    @Override
    public Mono<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .map(userFound -> new User(
                        userFound.getIdUser(),
                        userFound.getName(),
                        userFound.getLastName(),
                        userFound.getEmail(),
                        userFound.getPassword(),
                        userFound.getDocumentUser(),
                        userFound.getBaseSalary(),
                        userFound.getFechaNacimiento(),
                        userFound.getDireccion(),
                        userFound.getTelefono(),
                        userFound.getIdRole()
                ))
                .switchIfEmpty(
                        Mono.error(new RuntimeException("El email no existe"))
                );
    }

    @Override
    public Mono<User> findUserById(Long idUser) {
        return userRepository.findUserById(idUser)
                .map(userFound -> new User(
                        userFound.getIdUser(),
                        userFound.getName(),
                        userFound.getLastName(),
                        userFound.getEmail(),
                        userFound.getPassword(),
                        userFound.getDocumentUser(),
                        userFound.getBaseSalary(),
                        userFound.getFechaNacimiento(),
                        userFound.getDireccion(),
                        userFound.getTelefono(),
                        userFound.getIdRole()
                ))
                .switchIfEmpty(
                        Mono.error(new RuntimeException("El usuario no existe"))
                );
    }


    public Mono<User> editUser(User user) {

        if (user.getIdUser() == null) {
            return Mono.error(new RuntimeException("El Id del usuario es obligatorio para editar"));
        }
        if (user.getName() == null || user.getName().isBlank() ||
                user.getLastName() == null || user.getLastName().isBlank() ||
                user.getEmail() == null || user.getEmail().isBlank() ||
                user.getPassword() == null || user.getPassword().isBlank() ||
                user.getDocumentUser() == null || user.getBaseSalary() == null ||
                user.getFechaNacimiento() == null || user.getDireccion() == null ||
                user.getDireccion().isBlank() || user.getTelefono() == null ||
                user.getTelefono().isBlank() || user.getIdRole() == null) {
            return Mono.error(new RuntimeException("Campos obligatorios faltantes"));
        }
        if (!user.getEmail().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}")) {
            return Mono.error(new RuntimeException("Email inválido"));
        }
        if (user.getBaseSalary() < 0 || user.getBaseSalary() > 15_000_000) {
            return Mono.error(new RuntimeException("Salario fuera de rango"));
        }

        return userRepository.findUserById(user.getIdUser())
                .switchIfEmpty(Mono.error(new RuntimeException("El usuario no existe")))
                .flatMap(existingUser ->
                        userRepository.findUserByEmail(user.getEmail())
                                .filter(emailUser -> !emailUser.getIdUser().equals(user.getIdUser()))
                                .flatMap(duplicateEmail -> Mono.<User>error(new RuntimeException("El email ya está registrado en otro usuario")))
                                .switchIfEmpty(userRepository.editUser(user))
                );
    }
}
