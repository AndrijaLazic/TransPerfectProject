package com.TextPolish.demo.Model.Response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PolishResponse {
    @JsonProperty("polished_content")
    public String polishedContent;

    @JsonProperty("similarity")
    public double similarity;
}
