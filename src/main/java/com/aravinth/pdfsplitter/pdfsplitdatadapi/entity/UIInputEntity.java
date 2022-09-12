package com.aravinth.pdfsplitter.pdfsplitdatadapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "UI_INPUT")
public class UIInputEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="INPUT_ID")
    private Long inputDataID;
    @Column(name="FILE_ID")
    private String fileId;
    @Column(name="FILE_NAME")
    private String fileName;
    @Column(name="FILE_SIZE")
    private String fileSize;
    @Column(name="TOT_PAGES")
    private Integer totalPages;
    @Column(name="UPD_DTTM")
    private String uploadedOn;
}
