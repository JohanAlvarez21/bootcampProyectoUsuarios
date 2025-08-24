package co.com.proyectousuarios.api;

import co.com.proyectousuarios.api.dto.auth.LoginDto;
import co.com.proyectousuarios.usecase.auth.IAuthUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class AuthHandler {

    private final IAuthUseCase authUseCase;

    public Mono<ServerResponse> login(ServerRequest request) {
        return request.bodyToMono(LoginDto.class) // LoginDto está en infrastructure
                .flatMap(dto -> authUseCase.login(dto.email(), dto.password()))
                .flatMap(token -> ServerResponse.ok()
                        .bodyValue(Map.of("token", token)))
                .onErrorResume(e -> ServerResponse.status(HttpStatus.UNAUTHORIZED)
                        .bodyValue(Map.of("error", e.getMessage())));
    }
}

