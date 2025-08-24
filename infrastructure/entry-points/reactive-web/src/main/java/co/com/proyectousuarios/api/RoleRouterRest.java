package co.com.proyectousuarios.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RoleRouterRest {
    @Bean
    public RouterFunction<ServerResponse> roleRouterFunction(RoleHandler roleHandler) {
        return route(GET("/api/roles/listRoles"), roleHandler::listRoles)
                .andRoute(POST("/api/roles/saveRole"), roleHandler::saveRole);
    }
}
