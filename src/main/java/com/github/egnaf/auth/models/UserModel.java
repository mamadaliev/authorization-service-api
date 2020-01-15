package com.github.egnaf.auth.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection = "users")
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserModel extends BaseModel {

    private String username;
    private String email;
    private String password;
    private Set<RoleModel> roles;
    private String refreshToken;
    private long lastVisit;
}
