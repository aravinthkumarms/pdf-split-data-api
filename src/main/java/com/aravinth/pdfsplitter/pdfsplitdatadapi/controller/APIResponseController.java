package com.aravinth.pdfsplitter.pdfsplitdatadapi.controller;


import com.aravinth.pdfsplitter.pdfsplitdatadapi.entity.UIInputEntity;
import com.aravinth.pdfsplitter.pdfsplitdatadapi.response.BlobIdResponse;
import com.aravinth.pdfsplitter.pdfsplitdatadapi.response.DownloadFileResponse;
import com.aravinth.pdfsplitter.pdfsplitdatadapi.response.InputUIResponse;
import com.aravinth.pdfsplitter.pdfsplitdatadapi.service.ApiCallService;
import com.aravinth.pdfsplitter.pdfsplitdatadapi.service.DataService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("")
@Slf4j
@CrossOrigin()
public class APIResponseController {

    private ObjectMapper objectMapper;

    public APIResponseController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    private ApiCallService apiCallService;

    @Autowired
    private DataService dataService;

    @GetMapping("download/file")
    public DownloadFileResponse downloadFileResponse(@RequestParam("filename") String fileName){
        DownloadFileResponse dFR = null;
        dFR = apiCallService.downloadFile(fileName);
        return dFR;
    }

    @GetMapping("blob/response/v1")
    public BlobIdResponse blobIdResponse(@RequestParam String fileId){
        BlobIdResponse response = dataService.blobIdResponse(fileId);
        return response;
    }


    @GetMapping("blob/response/v2")
    public BlobIdResponse fileNameResponse(@RequestParam String fileName){
        BlobIdResponse response = dataService.fileNameResponse(fileName);
        return response;
    }

    @GetMapping("image/response/{fileId}")
    public HashMap<String, String> fileIDImageResponse(@PathVariable String fileId){
        log.info("Response for Cluster UI called");
        HashMap<String, String> imageResponse= apiCallService.fileIDImageResponse(fileId);
        return imageResponse;
    }

    @GetMapping("UI/input/{fileName}")
    public InputUIResponse inputUIResponse(@PathVariable String fileName){
        InputUIResponse response = apiCallService.inputUIResponse(fileName+".pdf");
        return response;
    }


    @ApiOperation(value="Giving input details for UI ",response= UIInputEntity.class)
    @ApiResponses(value={
            @ApiResponse(code = 200,message = "ok"),
            @ApiResponse(code = 400,message = "Bad Request"),
            @ApiResponse(code = 404,message = "Not Found"),
            @ApiResponse(code = 500,message = "Internal Server Error"),
    })
    @GetMapping("UI/input")
    public List<UIInputEntity> allInputUIResponse(){
        List<UIInputEntity> response = apiCallService.allFileInputUIResponse();
        return response;
    }

    @ApiOperation(value="Response for the schedular to get new docs to process ",response= UIInputEntity.class)
    @ApiResponses(value={
            @ApiResponse(code = 200,message = "ok"),
            @ApiResponse(code = 400,message = "Bad Request"),
            @ApiResponse(code = 404,message = "Not Found"),
            @ApiResponse(code = 500,message = "Internal Server Error"),
    })
    @GetMapping("get/filenames/to/process")
    public List<String> fileNameResponse(){
        List<String> fileNames = apiCallService.responseToBeProcessed();
        return fileNames;
    }
}
