package co.com.proyectousuarios.usecase.auth;

import co.com.proyectousuarios.model.token.gateways.ITokenService;
import co.com.proyectousuarios.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class AuthUseCase implements IAuthUseCase{

    private final UserRepository userRepository;
    private final ITokenService tokenService;

    @Override
    public Mono<String> login(String email, String password) {
        return userRepository.findUserByEmail(email)
                .switchIfEmpty(Mono.error(new RuntimeException("Usuario no encontrado")))
                .flatMap(user -> {
                    if (!user.getPassword().equals(password)) {
                        return Mono.error(new RuntimeException("Contraseña incorrecta"));
                    }
                    String token = tokenService.generateToken(user);
                    return Mono.just(token);
                });
    }
}
