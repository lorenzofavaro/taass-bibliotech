package taass.bibliotech.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import taass.bibliotech.authservice.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
