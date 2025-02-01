package com.TextPolish.demo.Model.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProofreadResponse {
    @JsonProperty("polished_content")
    public String PolishedContent;
}
