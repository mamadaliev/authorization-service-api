package com.github.egnaf.aas.transfers;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserTransfer {

    private long id;
    private String username;
    private String email;
    private List<String> roles;
    private String status;
    private Date created;
    private Date updated;
}
