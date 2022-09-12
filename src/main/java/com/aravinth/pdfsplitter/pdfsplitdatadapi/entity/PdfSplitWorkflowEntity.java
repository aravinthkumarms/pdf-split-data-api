package com.aravinth.pdfsplitter.pdfsplitdatadapi.entity;


import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Data
@Table(name="PDF_SPLIT_WRKFLW")
@EntityListeners(AuditingEntityListener.class)
public class PdfSplitWorkflowEntity extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WRKFLW_DTL_ID")
    private Long workFlowDtlId;

    @Column(name = "BLOB_ID")
    private Long blobId;

    @Column(name="WRKFLW_STEP_TYPE_ID")
    private Long wrkFlwStepTypeId;

    @Column(name="WRKFLW_STS_TYPE_ID")
    private Long wrkFlwStsTypeId;

    @Column(name="ERR_DESC")
    private String errorDescription;

    @Column(name="CURR_IND")
    private String currInd;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "BLOB_ID",unique = true,insertable = false,updatable = false)
    private IngestDataEntity ingestDataEntity;

}
