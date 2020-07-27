package com.nnk.springboot.services;

import com.nnk.springboot.domain.AppUserDetails;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private final UserRestRepository userRestRepository;

    @Autowired
    public AppUserDetailsService(UserRestRepository userRestRepository) {
        this.userRestRepository = userRestRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRestRepository.findByUsername(username);
        return new AppUserDetails(user.orElseThrow(() -> new UsernameNotFoundException(username)));
    }
}
