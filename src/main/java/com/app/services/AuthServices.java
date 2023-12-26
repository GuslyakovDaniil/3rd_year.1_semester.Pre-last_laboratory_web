package com.app.services;

import com.app.dtos.UserRegistration;
import com.app.enums.Role;
import com.app.models.Users;
import com.app.repositories.RoleRepository;
import com.app.repositories.UsersRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class AuthServices {

    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServices(UsersRepository usersRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public void register(UserRegistration registrationDTO) {
        if (!registrationDTO.getPassword().equals(registrationDTO.getConfirmPassword())) {
            throw new RuntimeException("passwords.match");
        }

        Optional<Users> byEmail = this.usersRepository.findByEmail(registrationDTO.getEmail());

        if (byEmail.isPresent()) {
            throw new RuntimeException("email.used");
        }

        var role = roleRepository.findRoleByName(Role.USER).orElseThrow();

        Users users = new Users(
                registrationDTO.getUsername(),
                passwordEncoder.encode(registrationDTO.getPassword()),
                registrationDTO.getEmail(),
                registrationDTO.getFirst_name(),
                registrationDTO.getLast_name()
        );

        users.setIs_active(true);

        users.setRole(Collections.singletonList(role.getName()));

        this.usersRepository.save(users);
    }

    public Users getUser(String username) {
        return usersRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " was not found!"));
    }
}
