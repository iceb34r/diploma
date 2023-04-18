package com.grisha.security.services;

import com.grisha.security.UserDetailsImpl;
import com.grisha.security.entities.Role;
import com.grisha.security.exceptions.NotFoundException;
import com.grisha.security.exceptions.UserAlreadyExistExcpetion;
import com.grisha.security.repositories.RolesRepository;
import com.grisha.security.repositories.UserRepository;
import com.grisha.security.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RolesRepository roleRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("The username with email %s doesn't exist", email));
        }
        return new UserDetailsImpl(user);
    }
    public User findUserById(Long id) {
        Optional<User> userFromDb = userRepository.findById(id);
        return userFromDb.orElseThrow(()-> new NotFoundException(String.format("Can't find user with id [%d]",id)));
    }

    public User findUserByEmail(String email) {
        Optional<User> userFromDb = userRepository.findByEmail(email);
        return userFromDb.orElseThrow(()-> new NotFoundException(String.format("Can't find user with email %s: ", email)));
    }

    public List<User> allUsers() { return userRepository.findAll(); }

    public User create(User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new UserAlreadyExistExcpetion("User already exist");
        }
        return userRepository.save(user);
    }
    public void update(User user) {
        userRepository.save(user);
    }
    public void delete(String email) { userRepository.deleteByEmail(email); }
}
