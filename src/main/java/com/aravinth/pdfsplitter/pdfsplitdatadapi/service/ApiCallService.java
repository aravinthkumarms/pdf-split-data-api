package com.aravinth.pdfsplitter.pdfsplitdatadapi.service;


import com.aravinth.pdfsplitter.pdfsplitdatadapi.entity.IngestDataEntity;
import com.aravinth.pdfsplitter.pdfsplitdatadapi.entity.UIInputEntity;
import com.aravinth.pdfsplitter.pdfsplitdatadapi.repository.IngestDataRepository;
import com.aravinth.pdfsplitter.pdfsplitdatadapi.repository.PdfSplitWorkflowRepository;
import com.aravinth.pdfsplitter.pdfsplitdatadapi.repository.UIInputRepository;
import com.aravinth.pdfsplitter.pdfsplitdatadapi.response.DownloadFileResponse;
import com.aravinth.pdfsplitter.pdfsplitdatadapi.response.InputUIResponse;
import com.aravinth.pdfsplitter.pdfsplitdatadapi.utils.HTTPAgent;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

@Service
@Slf4j
public class ApiCallService {

    @Value("${GCP_STORAGE_PATH}")
    private String location;

    @Autowired
    public HTTPAgent httpAgent;
    @Autowired
    @Value("${DATA_API_HOST}")
    public  String dataApiHost;

    @Autowired
    @Value("${IMAGE_API_HOST}")
    public String imageApiHost;

    @Autowired
    @Value("${AUTH}")
    public String auth;

    @Autowired
    private IngestDataRepository ingestDataRepository;


    private IngestDataEntity ingestDataEntity;

    @Autowired
    private PdfSplitWorkflowRepository pdfSplitWorkflowRepository;
    @Autowired
    private UIInputRepository uiInputRepository;
    private  ObjectMapper objectMapper;
    public ApiCallService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    public DownloadFileResponse downloadFile(String fileName){
        log.info("Calling API {}",dataApiHost);
        String url =  dataApiHost + "download/file/" +fileName;
        log.info(url);
        httpAgent.get(url, DownloadFileResponse.class);
        DownloadFileResponse downloadFileResponse = new DownloadFileResponse();
        downloadFileResponse.setFileName(fileName);
        downloadFileResponse.setFilePath(location+fileName);
        return downloadFileResponse;
    }



    public InputUIResponse inputUIResponse(String fileName){
        log.info("Response for Input UI called");
        IngestDataEntity response = ingestDataRepository.findByfileName(fileName);
        InputUIResponse outputResponse = new InputUIResponse();
        outputResponse.setFileId(response.getFileID());
        outputResponse.setFileName(response.getFileName());
        outputResponse.setFileSize(response.getFileSize());
        outputResponse.setTotalPages(response.getTotalPages());
        outputResponse.setUploadedOn(response.getUpdatedDate());
        log.info("Response for filename {} - {}",fileName,outputResponse);
        return outputResponse;
    }
    @SneakyThrows
    public List<UIInputEntity> allFileInputUIResponse(){
        log.info("Response for all Input UI called");
        List<UIInputEntity> responses = uiInputRepository.findAll();
        List<UIInputEntity> inputUIResponses = new ArrayList<>();
        for (UIInputEntity i:responses) {
            inputUIResponses.add(i);
        }
        log.info("Response {}",objectMapper.writeValueAsString(inputUIResponses));
        return inputUIResponses;
    }
    @SneakyThrows
    public List<String> responseToBeProcessed(){
        log.info("Filename reponse called");
        List<Long> blobIds = pdfSplitWorkflowRepository.findByToBeProcessed();
        List<String> fileNames = ingestDataRepository.findAllFileNameById(blobIds);
        log.info("Filenames to be processed - {}",fileNames);
        return fileNames;
    }

    public HashMap<String, String> fileIDImageResponse(String fileId) {
        IngestDataEntity ingestDataEntity = ingestDataRepository.findByfileID(fileId);
        String fileName = ingestDataEntity.getFileName();
        log.info("{}", fileName);
        Integer totalPages = ingestDataEntity.getTotalPages();
        HashMap<String, String> imageHashMap = new HashMap<>();
        for (int page = 1; page <= totalPages; page++) {
            String pageNum = "Page_" + String.valueOf(page);
            imageHashMap.put(pageNum, imageApiHost + fileName + "/thumbanails/" + pageNum + ".webp" + auth);
            log.info(imageHashMap.get(pageNum));
        }
        log.info("Response for fileId {} - {}",fileId,imageHashMap);
        return imageHashMap;
    }

}