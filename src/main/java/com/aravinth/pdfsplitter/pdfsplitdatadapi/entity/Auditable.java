package com.aravinth.pdfsplitter.pdfsplitdatadapi.entity;


import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Data
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class Auditable {

    @CreatedBy
    @Column(name = "CRE_BY_NM")
    private String createdByName;

    @Column(name = "CRE_DTTM")
    private Date createdDate = new Date();

    @LastModifiedBy
    @Column(name = "UPD_BY_NM")
    private String updateByName;

    @Column(name = "UPD_DTTM")
    private Date updatedDate = new Date();
}
