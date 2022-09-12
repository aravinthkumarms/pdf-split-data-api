package com.aravinth.pdfsplitter.pdfsplitdatadapi.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadFileResponse {
    private String FileName;
    private Date date = new Date();
    private String FilePath;
}
