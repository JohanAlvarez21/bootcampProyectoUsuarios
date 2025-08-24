package co.com.proyectousuarios.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class UserRouterRest {
    @Bean
    public RouterFunction<ServerResponse> userRouterFunction(UserHandler userHandler) {
        return route(GET("/api/users/listUsers"), userHandler::listUsers)
                .andRoute(POST("/api/users/saveUser"), userHandler::saveUser)
                .and(route(PUT("/api/users/editUser/{idUser}"), userHandler::editUser));
    }
}
