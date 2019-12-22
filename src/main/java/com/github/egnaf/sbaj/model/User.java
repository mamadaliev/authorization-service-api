package com.github.egnaf.sbaj.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username", unique = true, nullable = false)
    @Size(min = 4, max = 12, message = "Minimum username length: 4 characters")
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password")
    @Size(min = 8, message = "Minimum password length: 8 characters")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;
}
