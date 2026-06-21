package com.study.jwt_imp.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.study.jwt_imp.dto.UserDto;
import com.study.jwt_imp.model.Role;
import com.study.jwt_imp.model.User;
import com.study.jwt_imp.repository.UserRepository;
import com.study.jwt_imp.security.AuthenticatedUser;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService implements UserDetailsService {


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

        @Autowired
        public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
            this.userRepository = userRepository;
            this.passwordEncoder = passwordEncoder;
        }

    public boolean registerUser(UserDto userDto){
        User u = new User();
        u.setUsername(userDto.username());
        u.setEmail(userDto.email());
        u.setPassword(passwordEncoder.encode(userDto.password()));
        u.setRole(Role.valueOf(userDto.role()));

        userRepository.save(u);
        return true;
    
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
     User u = userRepository.findByUsername(username);
     return new AuthenticatedUser(u);
    }


}
