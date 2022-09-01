package taass.bibliotech.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taass.bibliotech.userservice.entity.User;
import taass.bibliotech.userservice.model.UserForm;
import taass.bibliotech.userservice.service.UserService;
import taass.bibliotech.userservice.utility.RestUtility;

import static taass.bibliotech.userservice.utility.RestUtility.HEADER_AUTH;

@CrossOrigin("*")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public ResponseEntity<User> getUser(@RequestHeader(HEADER_AUTH) String tokenHeader) throws Exception {
        Long accountId = RestUtility.getUserId(tokenHeader);
        User user = userService.getUser(accountId);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/user")
    public HttpEntity<String> updateUser(@RequestHeader(HEADER_AUTH) String tokenHeader, @RequestBody UserForm userForm) throws Exception {
        Long accountId = RestUtility.getUserId(tokenHeader);
        userService.editUser(accountId, userForm);
        return ResponseEntity.ok("User updated");
    }

    @DeleteMapping("/user")
    public HttpEntity<String> deleteUser(@RequestHeader(HEADER_AUTH) String tokenHeader) throws Exception {
        Long accountId = RestUtility.getUserId(tokenHeader);
        userService.deleteUser(accountId);
        return ResponseEntity.ok("User deleted");
    }
}
