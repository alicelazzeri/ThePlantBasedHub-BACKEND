package it.epicode.capstone_project_alicelazzeri.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.epicode.capstone_project_alicelazzeri.entities.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name="users")
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
// @Builder(setterPrefix = "with")
@JsonIgnoreProperties({"password", "active", "authorities", "enabled", "credentialsNonExpired", "accountNonExpired", "accountNonLocked"})

public class User extends BaseEntity implements UserDetails {

    @Column(nullable = false)
    @Size(min = 3, max = 30, message = "First name should be between 3 and 30 characters")
    private String firstName;
    @Column(nullable = false)
    @Size(min = 3, max = 30, message = "Last name should be between 3 and 30 characters")
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
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
