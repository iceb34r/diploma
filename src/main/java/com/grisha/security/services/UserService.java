package com.grisha.security.services;

import com.grisha.security.UserDetailsImpl;
import com.grisha.security.entities.Role;
import com.grisha.security.exceptions.NotFoundException;
import com.grisha.security.repositories.RolesRepository;
import com.grisha.security.repositories.UserRepository;
import com.grisha.security.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RolesRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("The username with email %s doesn't exist", email));
        }
//        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//        for (Role role : user.getRoles()) {
//            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
//        }
        return new UserDetailsImpl(user);
    }
    public User findUserById(Long id) {
        Optional<User> userFromDb = userRepository.findById(id);
        return userFromDb.orElseThrow(()-> new NotFoundException(String.format("Can't find user with id [%d]",id)));
    }

    public User findUserByEmail(String email) {
        Optional<User> userFromDb = userRepository.findByEmail(email);
        return userFromDb.orElseThrow(()-> new NotFoundException(String.format("Can't find user with email : %s ", email)));
//        return userFromDb.orElse(null);
    }

    public List<User> allUsers() { return userRepository.findAll(); }

    public void create(User user) {

//        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
//        if (existingUser != null) {
//            throw new UserAlreadyExistExcpetion("User already exist");
//        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roleRepository.findRoleById(2L));
        userRepository.save(user);
    }
    public void update(User user) {
        userRepository.save(user);
    }
    public void delete(String email) { userRepository.deleteByEmail(email); }
}
