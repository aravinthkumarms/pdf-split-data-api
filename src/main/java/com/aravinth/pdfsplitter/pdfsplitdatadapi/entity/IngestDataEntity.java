package com.aravinth.pdfsplitter.pdfsplitdatadapi.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name= "INGS_PDF")
public class IngestDataEntity extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BLOB_ID")
    private Long BlobID;

    @Column(name="FILE_ID")
    private String fileID;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "FILE_PATH")
    private String filePath;

    @Column(name = "TOT_PAGES")
    private Integer totalPages;

    @Column(name="FILE_SIZE")
    private Integer fileSize;



}
