package com.aravinth.pdfsplitter.pdfsplitdatadapi.repository;

import com.aravinth.pdfsplitter.pdfsplitdatadapi.entity.LookUpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LookUpRepository extends JpaRepository<LookUpEntity,Long> {
    @Query(value = "SELECT LKP_VAL_CD FROM INGS_DTL.LKP_VAL WHERE LKP_VAL_DESC=:typeName",nativeQuery = true)
    Long findByWorkFlowStepType(@Param("typeName") String typeName);
    @Query(value = "SELECT LKP_VAL_CD FROM INGS_DTL.LKP_VAL WHERE LKP_VAL_DESC=:typeName",nativeQuery = true)
    Long findByWorkFlowStatusType(@Param("typeName") String typeName);
}
