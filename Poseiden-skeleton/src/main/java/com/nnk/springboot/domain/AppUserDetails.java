package com.nnk.springboot.domain;

import com.nnk.springboot.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class AppUserDetails implements UserDetails {

    private final String username; // User Mail, and not User username
    private final String password; // Encoded password
    private final Collection<? extends GrantedAuthority> roles;


    public AppUserDetails(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        ArrayList<Role> rolesList = new ArrayList<>();
        String[] stringRoles = user.getRole().split(",");
        for (String role :
                stringRoles) {
            rolesList.add(Role.valueOf(role));
        }

        this.roles = rolesList;
    }

    public AppUserDetails(String username, String password, Collection<? extends GrantedAuthority> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
