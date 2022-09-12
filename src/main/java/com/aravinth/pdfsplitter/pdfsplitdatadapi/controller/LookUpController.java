package com.aravinth.pdfsplitter.pdfsplitdatadapi.controller;


import com.aravinth.pdfsplitter.pdfsplitdatadapi.entity.LookUpEntity;
import com.aravinth.pdfsplitter.pdfsplitdatadapi.response.LookUpResponse;
import com.aravinth.pdfsplitter.pdfsplitdatadapi.service.DataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("")
@Slf4j
public class LookUpController {

        @Autowired
        private DataService dataservice;

        @PostMapping("POST/lkp/val")
        public ResponseEntity<LookUpEntity> saveLookUp(@RequestBody LookUpEntity lookUpEntity){
            log.info("Calling DataService.saveLookUpData for saving Look up details");
            LookUpEntity savedData =  dataservice.saveLookUpData(lookUpEntity);
            return new ResponseEntity(savedData, HttpStatus.CREATED);
        }

        @GetMapping("lkp/val/{stepType}/{statusType}")
    public ResponseEntity<LookUpResponse> getLookUp(@PathVariable String stepType,@PathVariable String statusType){
            log.info("Getting Look Up values for {}, {}",statusType,stepType);
            LookUpResponse lookUpResponse = dataservice.lookUpResponse(stepType,statusType);
            return new ResponseEntity(lookUpResponse,HttpStatus.ACCEPTED);
        }
}
