package com.grisha.security;

import com.grisha.security.entities.User;
import lombok.*;

@Getter
@Setter
public class UserDetailsImpl extends org.springframework.security.core.userdetails.User {

    public UserDetailsImpl (User user) {
        super(user.getEmail(), user.getPassword(),
                true, true ,true,
                true, user.getRoles() );
    }
}
