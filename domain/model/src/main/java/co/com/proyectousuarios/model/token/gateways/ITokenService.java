package co.com.proyectousuarios.model.token.gateways;

import co.com.proyectousuarios.model.user.User;

public interface ITokenService {
    String generateToken(User user);
}
