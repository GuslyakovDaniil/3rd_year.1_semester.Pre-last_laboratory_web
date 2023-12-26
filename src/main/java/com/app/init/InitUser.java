package com.app.init;

import com.app.enums.Role;
import com.app.models.Roles;
import com.app.models.Users;
import com.app.repositories.RoleRepository;
import com.app.repositories.UsersRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class InitUser implements CommandLineRunner {
    private final UsersRepository usersRepository;

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final String defaultPassword;

    public InitUser(UsersRepository usersRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, @Value("${app.default.password}") String defaultPassword) {
        this.usersRepository = usersRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.defaultPassword = defaultPassword;
    }

    @Override
    public void run(String... args) throws Exception {
        initRoles();
        initUsers();
    }

    private void initRoles() {
        if (roleRepository.count() == 0) {
            var moderatorRole = new Roles(Role.MODERATOR);
            var normalUserRole = new Roles(Role.USER);
            roleRepository.save(moderatorRole);
            roleRepository.save(normalUserRole);
        }
    }

    private void initUsers() {
        if (usersRepository.count() == 0) {
            initModerator();
            initNormalUser();
        }
    }



    private void initModerator(){

        var moderatorRole = roleRepository.
                findRoleByName(Role.MODERATOR).orElseThrow();

        var moderatorUser = new Users("moderator", passwordEncoder.encode(defaultPassword), "moderator@mail.ru", "Moder", "ppppp");
        moderatorUser.setRole(Collections.singletonList(moderatorRole.getName()));

        usersRepository.save(moderatorUser);
    }

    private void initNormalUser(){
        var userRole = roleRepository.
                findRoleByName(Role.USER).orElseThrow();

        var normalUser = new Users("user", passwordEncoder.encode(defaultPassword), "user@mail.ru", "User", "ppppp");
        normalUser.setRole(Collections.singletonList(userRole.getName()));

        usersRepository.save(normalUser);
    }
}
