package taass.bibliotech.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import taass.bibliotech.authservice.entity.User;
import taass.bibliotech.authservice.models.AuthenticationRequest;
import taass.bibliotech.authservice.models.AuthenticationRequestGoogle;
import taass.bibliotech.authservice.models.AuthenticationResponse;
import taass.bibliotech.authservice.models.RegistrationRequest;
import taass.bibliotech.authservice.repository.UserRepository;
import taass.bibliotech.authservice.service.UserService;
import taass.bibliotech.authservice.utility.JwtUtil;
import taass.bibliotech.authservice.utility.RestUtility;

import javax.validation.Valid;

import java.util.Optional;

import static taass.bibliotech.authservice.utility.RestUtility.HEADER_AUTH;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @GetMapping({"/"})
    public String hello() {
        return ("<h1>Hello world</h1>");
    }

    @GetMapping({"/user"})
    public String user(@RequestHeader(HEADER_AUTH) String tokenHeader) {
        Long accountId = RestUtility.getUserId(tokenHeader);
        return ("Hello user " + accountId);
    }

    @GetMapping({"/admin"})
    public String admin() {
        return ("Hello admin");
    }


    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect email or password");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final Long userId = userRepository.findByEmail(authenticationRequest.getEmail()).get().getId();
        final String jwt = jwtTokenUtil.generateToken(userDetails, userId);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public HttpEntity<String> signUp(@Valid @RequestBody RegistrationRequest registrationRequest) throws Exception {
        userService.signUp(registrationRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Account successfully registered!");
    }

    @PostMapping("/googlelogin")
    public ResponseEntity<?> authenticateWithGoogle(@RequestBody AuthenticationRequestGoogle authenticationRequest) throws Exception {

        final Optional<User> userOpt = userRepository.findByEmail(authenticationRequest.getEmail());

        if (userOpt.isEmpty()) { // Nuova registrazione
            userService.googleSignUp(authenticationRequest);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final Long userId = userRepository.findByEmail(authenticationRequest.getEmail()).get().getId();
        final String jwt = jwtTokenUtil.generateToken(userDetails, userId);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
