package com.aravinth.pdfsplitter.pdfsplitdatadapi.controller;


import com.aravinth.pdfsplitter.pdfsplitdatadapi.entity.UIInputEntity;
import com.aravinth.pdfsplitter.pdfsplitdatadapi.request.PdfSplitWorkFlowRequest;
import com.aravinth.pdfsplitter.pdfsplitdatadapi.response.BlobIdResponse;
import com.aravinth.pdfsplitter.pdfsplitdatadapi.response.InputUIResponse;
import com.aravinth.pdfsplitter.pdfsplitdatadapi.response.PdfSplitWorkflowResponse;
import com.aravinth.pdfsplitter.pdfsplitdatadapi.service.DataService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequestMapping("")
@CrossOrigin( )
public class DataApiController {

    @Autowired
    private DataService dataService;




    @PostMapping("blob/createwrkflw/{fileId}/v1")
    public PdfSplitWorkflowResponse pdfSplitWorkflowResponse(@PathVariable String fileId, @RequestBody PdfSplitWorkFlowRequest pdfSplitWorkFlowRequest){
        log.info(fileId,pdfSplitWorkFlowRequest);
        log.info("Creating workflow for the fileId - {}",fileId);
        PdfSplitWorkflowResponse response = dataService.createWorkFlowForPdf(fileId,pdfSplitWorkFlowRequest);
        return  response;
    }



}
