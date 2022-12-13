package com.rodrigopisati.Final_backend.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;


    @Column
    private String name, username, email, password;


    @Enumerated(EnumType.STRING)
    private UserRol userRol;

    public User(){
    }


    public User(String name, String username, String email, String password, UserRol userRol) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.userRol = userRol;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(userRol.name());
        return Collections.singletonList(grantedAuthority);
    }


    @Override
    public boolean isAccountNonExpired() {return true;}


    @Override
    public boolean isAccountNonLocked() {return true;}


    @Override
    public boolean isCredentialsNonExpired() {return true;}


    @Override
    public boolean isEnabled() {return true;}
}
