package taass.bibliotech.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import taass.bibliotech.userservice.entity.User;
import taass.bibliotech.userservice.model.UserForm;
import taass.bibliotech.userservice.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't exist"));

        return user;
    }

    @Override
    public void editUser(long id, UserForm userForm) {
        User user = getUser(id);
        if (userForm.getFirstName() != null) user.setFirstName(userForm.getFirstName());
        if (userForm.getLastName() != null) user.setLastName(userForm.getLastName());
        if (userForm.getEmail() != null) user.setEmail(userForm.getEmail());
        if (userForm.getPassword() != null) user.setPassword(userForm.getPassword());

        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
