package taass.bibliotech.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import taass.bibliotech.userservice.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
