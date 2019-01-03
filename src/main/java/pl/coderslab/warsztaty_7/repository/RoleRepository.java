package pl.coderslab.warsztaty_7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.warsztaty_7.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findRoleByName(String name);

}
