package com.github.egnaf.auth.models;

import com.github.egnaf.auth.utils.RandomIdentifier;
import com.github.egnaf.auth.utils.Status;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;

@Data
@SuperBuilder
public class BaseModel {

    @Id
    private String id;
    private Status status;
    private long created;
    private long updated;

    public BaseModel() {
        this.setId(RandomIdentifier.generate());
        this.status = Status.ACTIVE;
        this.created = new Timestamp(System.currentTimeMillis()).getTime();
        this.updated = new Timestamp(System.currentTimeMillis()).getTime();
    }
}
