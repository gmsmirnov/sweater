package ru.gsmirnov.sweater.domain;

import javax.persistence.*;
import java.util.Set;

/**
 * Describes user's entity.
 */
@Entity
@Table(name = "usr")
public class User {
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
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
