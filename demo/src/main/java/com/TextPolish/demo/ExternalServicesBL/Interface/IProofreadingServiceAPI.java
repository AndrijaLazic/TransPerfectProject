package com.TextPolish.demo.ExternalServicesBL.Interface;

import com.TextPolish.demo.Model.Request.PolishRequest;
import com.TextPolish.demo.Model.Response.ProofreadResponse;

public interface IProofreadingServiceAPI {
    public ProofreadResponse proofread(PolishRequest proofreadRequest);
    public void resetConfig();
    public String[] getLanguageCodes();
    public String[] getContentDomains();
}
