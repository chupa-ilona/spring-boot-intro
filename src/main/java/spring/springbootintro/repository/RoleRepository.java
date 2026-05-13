package spring.springbootintro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.springbootintro.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
