package spring.springbootintro.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import spring.springbootintro.model.Role;
import spring.springbootintro.model.RoleName;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);

}
