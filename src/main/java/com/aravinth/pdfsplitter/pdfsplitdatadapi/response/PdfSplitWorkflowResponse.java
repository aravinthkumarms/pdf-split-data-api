package com.aravinth.pdfsplitter.pdfsplitdatadapi.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PdfSplitWorkflowResponse {
    private String fileId;
    private Long workFlowDtlId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String workFlowStepType;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String workFlowStatusType;
    private Date createdDateTime;
    private String errorDescription;
}
