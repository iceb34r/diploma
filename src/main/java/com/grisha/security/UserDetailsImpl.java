package com.grisha.security;

import com.grisha.security.entities.User;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
public class UserDetailsImpl extends org.springframework.security.core.userdetails.User {

    private static final long serialVersionUID = 1L;

    public UserDetailsImpl (User user) {
        super(user.getEmail(), UUID.randomUUID().toString(),
                true, true ,true,
                true, user.getRoles() );
    }
}
