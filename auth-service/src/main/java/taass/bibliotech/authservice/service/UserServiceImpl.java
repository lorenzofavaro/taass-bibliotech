package taass.bibliotech.authservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taass.bibliotech.authservice.entity.User;
import taass.bibliotech.authservice.models.AuthenticationRequestGoogle;
import taass.bibliotech.authservice.models.RegistrationRequest;
import taass.bibliotech.authservice.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void signUp(RegistrationRequest registrationRequest) throws Exception{
        String email = registrationRequest.getEmail();

        if (userRepository.existsByEmail(email)) {
            throw new Exception("Account already exists");
        }

        User user = new User();
        user.setEmail(email);
        user.setFirstName(registrationRequest.getFirstName());
        user.setLastName(registrationRequest.getLastName());
        user.setPassword(registrationRequest.getPassword());
        user.setRoles("ROLE_USER");

        userRepository.save(user);
    }

    @Override
    public void googleSignUp(AuthenticationRequestGoogle authenticationRequest) {
        User user = new User();
        user.setEmail(authenticationRequest.getEmail());
        user.setFirstName(authenticationRequest.getFirstName());
        user.setLastName(authenticationRequest.getLastName());
        user.setRoles("ROLE_USER");

        userRepository.save(user);
    }
}
