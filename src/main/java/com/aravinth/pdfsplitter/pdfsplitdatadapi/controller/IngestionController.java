package com.aravinth.pdfsplitter.pdfsplitdatadapi.controller;


import com.aravinth.pdfsplitter.pdfsplitdatadapi.entity.IngestDataEntity;
import com.aravinth.pdfsplitter.pdfsplitdatadapi.repository.IngestDataRepository;
import com.aravinth.pdfsplitter.pdfsplitdatadapi.service.DataService;

import com.google.cloud.storage.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@Slf4j
public class IngestionController {
    @Autowired
    private IngestDataRepository ingestDataRepository;

    @Autowired
    private DataService dataService;

    @Value("${PROJECT_ID}")
    private  String projectId;

    @Value("${BUCKET_NAME}")
    private String bucketName;


    Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();

    @PostMapping("/upload-file")
    public ResponseEntity<String> uploadFile1(@RequestParam("file")MultipartFile file){
        String Name = file.getOriginalFilename();
        if (file.isEmpty()){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File is missing in request");
        }

        try{
            InputStream is = file.getInputStream();
            byte data[] = new byte[is.available()];
            is.read(data);
            BlobId blobId = BlobId.of(bucketName, file.getOriginalFilename());
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
            storage.create(blobInfo,data);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok("File uploaded Successfully");
    }


    @PostMapping("/write")
        public IngestDataEntity upload(@RequestBody IngestDataEntity IDE){
            IngestDataEntity ingestDataEntity = new IngestDataEntity();
            ingestDataEntity.setFileID("dfsdf");
            ingestDataEntity.setFileSize(60);
            ingestDataEntity.setFilePath("gs://"+bucketName+"/");
            ingestDataEntity.setFileName("sample");
            ingestDataRepository.save(ingestDataEntity);
            return  ingestDataEntity;
    }

    @PostMapping("/POST/upload/doc/details")
    public ResponseEntity<IngestDataEntity> saveData( @RequestBody IngestDataEntity ingestDataEntity){
        log.info("Calling Dataservice.saveUpdData for saving the upload data");
        IngestDataEntity savedData = dataService.saveUpdData(ingestDataEntity);
        log.info("Successfully saved {}",savedData);
        return new ResponseEntity(savedData,HttpStatus.CREATED);
    }
}
