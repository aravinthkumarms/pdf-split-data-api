package com.aravinth.pdfsplitter.pdfsplitdatadapi.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlobIdResponse {
    private Long blobId;
    private String fileName;
    private String fileId;
}
