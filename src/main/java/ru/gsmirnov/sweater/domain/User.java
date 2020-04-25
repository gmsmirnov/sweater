package ru.gsmirnov.sweater.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

/**
 * Describes user's entity.
 */
@Entity
@Table(name = "usr")
public class User implements UserDetails {
    /**
     * User's id in database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * User's name (login).
     */
    private String username;

    /**
     * User's password.
     */
    private String password;

    /**
     * User's activity sign.
     */
    private boolean active;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER) // not necessary to store enums in database
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id")) // creating help table "user_role", which will be created with usr-table by id
    @Enumerated(EnumType.STRING) // Enum will be sored like a String-value
    private Set<Role> roles;

    public User() {
    }

    public User(String username, String password, boolean active, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.active = active;
        this.roles = roles;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return this.username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.username);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getRoles();
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
        return this.isActive();
    }
}
