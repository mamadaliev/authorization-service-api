package com.github.egnaf.sbaj.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dozer.Mapping;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends Base {

    @Column(name = "username", unique = true, nullable = false, length = 12)
    @Size(min = 4, max = 12, message = "Minimum username length: 4 characters")
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password")
    @Size(min = 8, message = "Minimum password length: 8 characters")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;

    public User() {
        this.roles = new ArrayList<>();
    }

    public User(@Size(min = 4, max = 12, message = "Minimum username length: 4 characters") String username,
                String email,
                @Size(min = 8, message = "Minimum password length: 8 characters") String password,
                List<Role> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public List<Role> getRoles() {
        return roles;
    }

    @Mapping("roles")
    public List<String> getRolesArray() {
        return roles.stream().map(Role::getName).collect(Collectors.toList());
    }
}
