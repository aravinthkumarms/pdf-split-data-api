package com.aravinth.pdfsplitter.pdfsplitdatadapi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LKP_MAST")
public class LookUpMastEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "LKP_MAST_ID")
    private Long id;

    @Column(name = "LKP_MAST_NM")
    @JsonProperty("attribute")
    private String lookUpName;

}
