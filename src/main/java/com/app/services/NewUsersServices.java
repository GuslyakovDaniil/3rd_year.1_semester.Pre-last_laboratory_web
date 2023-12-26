package com.app.services;

import com.app.models.Users;
import com.app.repositories.UsersRepository;
import com.app.dtos.AddUsersDto;
import com.app.dtos.ShowUserInfoDto;
import com.app.dtos.ShowDetailedUserInfo;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewUsersServices {
    private final UsersRepository usersRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public NewUsersServices(UsersRepository usersRepository, ModelMapper mapper, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    @CacheEvict(cacheNames = "users", allEntries = true)
    public void addUsers(AddUsersDto addUsersDto) {
        Users user = mapper.map(addUsersDto, Users.class);
        user.setPassword(passwordEncoder.encode(addUsersDto.getPassword()));
        usersRepository.saveAndFlush(user);
    }

    @Cacheable("users")
    public List<ShowUserInfoDto> allUsers() {
        return usersRepository.findAll().stream().map(users -> mapper.map(users, ShowUserInfoDto.class))
                .collect(Collectors.toList());
    }

    @Cacheable("usersDetails")
    public ShowDetailedUserInfo usersDetails(String email) {
        return mapper.map(usersRepository.findByEmail(email).orElse(null), ShowDetailedUserInfo.class);
    }

    @CacheEvict(cacheNames = "users", allEntries = true)
    public void removeUser(String email) {
        usersRepository.deleteByEmail(email);
    }
}
