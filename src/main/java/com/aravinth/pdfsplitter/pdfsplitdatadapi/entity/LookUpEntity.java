package com.aravinth.pdfsplitter.pdfsplitdatadapi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "LKP_VAL")
public class LookUpEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "LKP_VAL_ID")
    private Long id;

    @Column(name="LKP_MAST_ID")
    private Long mastId;

    @Column(name = "LKP_VAL_NM")
    @JsonProperty("attribute")
    private String lookUpName;

    @Column(name = "LKP_VAL_CD")
    private String lookUpCode;

    @Column(name = "LKP_VAL_DESC")
    private String lookUpDesc;

    @Column(name = "CRE_DTTM")
    private Date createdDate = new Date();

    @LastModifiedBy
    @Column(name = "UPD_DTTM")
    private Date updatedDate = new Date();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "LKP_MAST_ID",unique = true,insertable = false,updatable = false)
    private LookUpMastEntity lookUpMastEntity;
}
