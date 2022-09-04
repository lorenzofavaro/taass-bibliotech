package taass.bibliotech.authservice.service;

import taass.bibliotech.authservice.models.AuthenticationRequestGoogle;
import taass.bibliotech.authservice.models.RegistrationRequest;

public interface UserService {

    void signUp(RegistrationRequest registrationRequest) throws Exception;

    void googleSignUp(AuthenticationRequestGoogle authenticationRequest);
}

