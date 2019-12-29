package com.github.egnaf.auth_api_service.models;

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
public class UserModel extends BaseModel {

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
    private List<RoleModel> roleModels;

    public UserModel() {
        this.roleModels = new ArrayList<>();
    }

    public UserModel(@Size(min = 4, max = 12, message = "Minimum username length: 4 characters") String username,
                     String email,
                     @Size(min = 8, message = "Minimum password length: 8 characters") String password,
                     List<RoleModel> roleModels) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roleModels = roleModels;
    }

    public List<RoleModel> getRoleModels() {
        return roleModels;
    }

    @Mapping("roles")
    public List<String> getRolesArray() {
        return roleModels.stream().map(RoleModel::getName).collect(Collectors.toList());
    }
}
