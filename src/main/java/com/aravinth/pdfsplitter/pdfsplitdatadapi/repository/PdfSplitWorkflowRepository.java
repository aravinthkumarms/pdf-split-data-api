package com.aravinth.pdfsplitter.pdfsplitdatadapi.repository;

import com.aravinth.pdfsplitter.pdfsplitdatadapi.entity.PdfSplitWorkflowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PdfSplitWorkflowRepository extends JpaRepository<PdfSplitWorkflowEntity,Long> {

    @Query (value = "SELECT * FROM INGS_DTL.PDF_SPLIT_WRKFLW WHERE BLOB_ID=:blobId",nativeQuery = true)
    List<PdfSplitWorkflowEntity>findByBlobIdIn(@Param("blobId")Long blobId);

    @Query (value ="SELECT BLOB_ID from INGS_DTL.PDF_SPLIT_WRKFLW where CURR_IND =1 and WRKFLW_STEP_TYPE_ID=4 and WRKFLW_STS_TYPE_ID = 2;",nativeQuery = true)
    List<Long>findByToBeProcessed();
}
