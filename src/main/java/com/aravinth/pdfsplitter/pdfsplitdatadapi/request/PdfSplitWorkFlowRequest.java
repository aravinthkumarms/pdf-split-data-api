package com.aravinth.pdfsplitter.pdfsplitdatadapi.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PdfSplitWorkFlowRequest {
    @NotEmpty(message = "WorkFlow Step Type cannot be Empty")
    private String workFlowStepType;
    @NotEmpty(message = "WorkFlow Status Type cannot be Empty")
    private String workFlowStatusType;

    private String errorDescription;
}
