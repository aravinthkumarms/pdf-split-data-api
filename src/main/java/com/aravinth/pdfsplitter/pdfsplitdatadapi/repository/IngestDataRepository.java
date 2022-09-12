package com.aravinth.pdfsplitter.pdfsplitdatadapi.repository;

import com.aravinth.pdfsplitter.pdfsplitdatadapi.entity.IngestDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngestDataRepository extends JpaRepository<IngestDataEntity,Long> {
    IngestDataEntity findByfileID(String fileId);
    IngestDataEntity findByfileName(String fileName);

    @Query(value = "SELECT FILE_NAME from INGS_DTL.INGS_PDF where BLOB_ID in (:blobIds)",nativeQuery = true)
    List<String> findAllFileNameById(@Param("blobIds") Iterable<Long> blobIds);
}
