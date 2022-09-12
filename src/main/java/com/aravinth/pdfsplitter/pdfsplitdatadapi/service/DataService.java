package com.aravinth.pdfsplitter.pdfsplitdatadapi.service;


import com.aravinth.pdfsplitter.pdfsplitdatadapi.entity.IngestDataEntity;
import com.aravinth.pdfsplitter.pdfsplitdatadapi.entity.LookUpEntity;
import com.aravinth.pdfsplitter.pdfsplitdatadapi.entity.PdfSplitWorkflowEntity;
import com.aravinth.pdfsplitter.pdfsplitdatadapi.entity.UIInputEntity;
import com.aravinth.pdfsplitter.pdfsplitdatadapi.repository.IngestDataRepository;
import com.aravinth.pdfsplitter.pdfsplitdatadapi.repository.LookUpRepository;
import com.aravinth.pdfsplitter.pdfsplitdatadapi.repository.PdfSplitWorkflowRepository;
import com.aravinth.pdfsplitter.pdfsplitdatadapi.repository.UIInputRepository;
import com.aravinth.pdfsplitter.pdfsplitdatadapi.request.PdfSplitWorkFlowRequest;
import com.aravinth.pdfsplitter.pdfsplitdatadapi.response.BlobIdResponse;
import com.aravinth.pdfsplitter.pdfsplitdatadapi.response.LookUpResponse;
import com.aravinth.pdfsplitter.pdfsplitdatadapi.response.PdfSplitWorkflowResponse;
import com.aravinth.pdfsplitter.pdfsplitdatadapi.utils.DateFormat;
import com.aravinth.pdfsplitter.pdfsplitdatadapi.utils.GeneratePDFID;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class DataService {

    private final ObjectMapper objectMapper;
    @Autowired
    private IngestDataRepository ingestDataRepository;

    @Autowired
    private DateFormat dateFormat;
    @Autowired
    private UIInputRepository uiInputRepository;
    @Autowired
    private PdfSplitWorkflowRepository pdfSplitWorkflowRepository;
    @Autowired
    private LookUpRepository lookUpRepository;

    public DataService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    @SneakyThrows
    public BlobIdResponse blobIdResponse(String fileId){
        log.info("Blob response for the fileId - {}",objectMapper.writeValueAsString(fileId));
        IngestDataEntity ingestDataEntity = ingestDataRepository.findByfileID(fileId);
        BlobIdResponse blobResponse = new BlobIdResponse();
        blobResponse.setBlobId(ingestDataEntity.getBlobID());
        blobResponse.setFileId(ingestDataEntity.getFileID());
        blobResponse.setFileName(ingestDataEntity.getFileName());
        log.info("Response for the {} is {}",fileId,objectMapper.writeValueAsString(blobResponse));
        return blobResponse;
    }

    @SneakyThrows
    public BlobIdResponse fileNameResponse(String fileName){
        log.info("Blob response for the fileName - {}",objectMapper.writeValueAsString(fileName));
        IngestDataEntity ingestDataEntity = ingestDataRepository.findByfileName(fileName);
        BlobIdResponse blobResponse = new BlobIdResponse();
        blobResponse.setBlobId(ingestDataEntity.getBlobID());
        blobResponse.setFileId(ingestDataEntity.getFileID());
        blobResponse.setFileName(ingestDataEntity.getFileName());
        log.info("Response for the {} is {}",fileName,objectMapper.writeValueAsString(blobResponse));
        return blobResponse;
    }



    @SneakyThrows
    public LookUpResponse lookUpResponse(String stepTypeName, String statusTypeName){
        Long lookUpEntityStatus = lookUpRepository.findByWorkFlowStatusType(statusTypeName);
        Long lookUpEntityStep= lookUpRepository.findByWorkFlowStepType(stepTypeName);
        LookUpResponse lookUpResponse = new LookUpResponse();
        lookUpResponse.setWorkFlowStatusTyepId(lookUpEntityStatus);
        lookUpResponse.setWorkFlowStepTypeId(lookUpEntityStep);
        log.info("Look Up Response {}",objectMapper.writeValueAsString(lookUpResponse));
        return lookUpResponse;
    }

    private static final DecimalFormat df = new DecimalFormat("0.00");
    public IngestDataEntity saveUpdData(IngestDataEntity ingestDataEntity){
        log.info("Saving doc upload details");
        ingestDataEntity.setFileID(generateFileID());
        ingestDataEntity.setUpdateByName("DATA-API");
        ingestDataEntity.setCreatedByName("DATA-API");
        UIInputEntity uiInputEntity = new UIInputEntity();
        log.info("Saving Data for UI Input");
        uiInputEntity.setFileId(ingestDataEntity.getFileID());
        uiInputEntity.setFileName(ingestDataEntity.getFileName());
        uiInputEntity.setFileSize(df.format((ingestDataEntity.getFileSize()/1024)/1024.f)+" MB");
        uiInputEntity.setTotalPages(ingestDataEntity.getTotalPages());
        uiInputEntity.setUploadedOn(dateFormat.dateFormat(ingestDataEntity.getUpdatedDate()));
        uiInputRepository.save(uiInputEntity);
        return ingestDataRepository.save(ingestDataEntity);
    }

    public LookUpEntity saveLookUpData(LookUpEntity lookUpEntity){
        log.info("Saving Look Up Data");
        return lookUpRepository.save(lookUpEntity);
    }

    @SneakyThrows
    public PdfSplitWorkflowResponse createWorkFlowForPdf(String fileId, PdfSplitWorkFlowRequest pdfSplitWorkFlowRequest){
        Long blobJd;
        String workFlowStepType = pdfSplitWorkFlowRequest.getWorkFlowStepType();
        String workFlowStatusType = pdfSplitWorkFlowRequest.getWorkFlowStatusType();
        String errorDescription = pdfSplitWorkFlowRequest.getErrorDescription();
        LookUpResponse response = lookUpResponse(workFlowStepType,workFlowStatusType);
        Long workFlowStepTypeId = response.getWorkFlowStepTypeId();
        Long workFlowStatusTyepId = response.getWorkFlowStatusTyepId();
        BlobIdResponse blobIdResponse= blobIdResponse(fileId);
        blobJd = blobIdResponse.getBlobId();
        List <PdfSplitWorkflowEntity> pdfSplitWorkflowEntities = pdfSplitWorkflowRepository.findByBlobIdIn(blobJd);
        PdfSplitWorkflowEntity pdfSplitWorkflowEntity = new PdfSplitWorkflowEntity();
        pdfSplitWorkflowEntity.setBlobId(blobJd);
        List<PdfSplitWorkflowEntity> entities = pdfSplitWorkflowEntities.stream().peek(record ->  record.setCurrInd("0")).collect(Collectors.toList());
        pdfSplitWorkflowEntity.setWrkFlwStepTypeId(workFlowStepTypeId);
        pdfSplitWorkflowEntity.setWrkFlwStsTypeId(workFlowStatusTyepId);
        pdfSplitWorkflowEntity.setCreatedByName("DATA-API");
        pdfSplitWorkflowEntity.setUpdateByName("DATA-API");
        pdfSplitWorkflowEntity.setCurrInd("1");
        pdfSplitWorkflowEntity.setErrorDescription(errorDescription);
        Date createdDateTime = pdfSplitWorkflowEntity.getCreatedDate();
        pdfSplitWorkflowRepository.save(pdfSplitWorkflowEntity);
        PdfSplitWorkflowResponse pdfSplitWorkflowResponse = new PdfSplitWorkflowResponse();
        pdfSplitWorkflowResponse.setWorkFlowDtlId(pdfSplitWorkflowEntity.getWorkFlowDtlId());
        pdfSplitWorkflowResponse.setFileId(fileId);
        pdfSplitWorkflowResponse.setWorkFlowStatusType(workFlowStatusType);
        pdfSplitWorkflowResponse.setWorkFlowStepType(workFlowStepType);
        pdfSplitWorkflowResponse.setCreatedDateTime(createdDateTime);
        pdfSplitWorkflowResponse.setErrorDescription(errorDescription);
        log.info("Workflow Response for the Blob ID - {}", objectMapper.writeValueAsString(pdfSplitWorkflowResponse));
        return pdfSplitWorkflowResponse;
    }


    public String generateFileID(){
        String fileId;
        fileId = GeneratePDFID.GeneratePDFGUIDId();
        return fileId;
    }

}
