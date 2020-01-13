package com.github.egnaf.auth.models;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty(value = "refresh_token")
    private String refreshToken;

    @JsonProperty(value = "last_visit")
    private long lastVisit;
}
