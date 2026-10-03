package com.shopnest.shopnest_api.common.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.*;

import java.time.Instant;

@Getter //Creates Getter of every variable
@Setter //Creates Setter of every variable
public class BaseDocument {
    @Id //for mongo _id
    private String id;
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;
    @CreatedBy
    private String createdBy;
    @LastModifiedBy
    private String updateBy;
    @Version
    private Long version;

}
