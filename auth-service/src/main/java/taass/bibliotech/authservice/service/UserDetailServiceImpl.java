package taass.bibliotech.authservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import taass.bibliotech.authservice.entity.User;
import taass.bibliotech.authservice.entity.UserDetailsImpl;
import taass.bibliotech.authservice.models.RegistrationRequest;
import taass.bibliotech.authservice.repository.UserRepository;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);

        user.orElseThrow(() -> new UsernameNotFoundException("Not found: "+ username));

        return user.map(UserDetailsImpl::new).get();
    }
}
