package taass.bibliotech.userservice.service;

import taass.bibliotech.userservice.entity.User;
import taass.bibliotech.userservice.model.UserForm;

public interface UserService {
    User getUser(Long id);

    void editUser(long id, UserForm userForm);

    void deleteUser(Long id);
}
