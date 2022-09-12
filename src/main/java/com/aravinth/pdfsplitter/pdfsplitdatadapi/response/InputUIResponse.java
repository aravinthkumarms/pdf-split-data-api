package com.aravinth.pdfsplitter.pdfsplitdatadapi.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputUIResponse {
    private String fileId;
    private String fileName;
    private Integer fileSize;
    private Integer totalPages;
    private Date uploadedOn = new Date();
}

