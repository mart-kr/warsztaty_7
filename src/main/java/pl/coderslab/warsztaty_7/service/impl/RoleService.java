package pl.coderslab.warsztaty_7.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.warsztaty_7.model.Role;
import pl.coderslab.warsztaty_7.repository.RoleRepository;


@Service
@Transactional
public class RoleService {

    private RoleRepository roleRepository;

    public Role findRoleByName(String name) {
        return roleRepository.findRoleByName(name);
    }

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
}
