package com.example.backend.models;

import com.example.backend.models.enums.Role;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "name")
    private String name;

    @Column(name = "active")
    private boolean active;

    @Column(name = "password", length = 1000)
    private String password;

    @Column(name = "isConfirmed")
    private boolean isConfirmed;

    @Column(name = "token", length = 1000)
    private String token;

    private LocalDateTime dateOfCreation;

    @PrePersist
    private void init() {
        dateOfCreation = LocalDateTime.now();
    }

    //security

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(Role.USER_ROLE, Role.ADMIN_ROLE, Role.MODERATOR_ROLE);
    }

    @Override
    public String getUsername() {
        return email;
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
        return active;
    }
}
