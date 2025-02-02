package com.TextPolish.demo.ExternalServicesBL.Implementation;

import com.TextPolish.demo.ExternalServicesBL.Interface.IProofreadingServiceAPI;
import com.TextPolish.demo.Model.Request.PolishRequest;
import com.TextPolish.demo.Model.Response.ProofreadResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@RequiredArgsConstructor
@Service
public class ProofreadingServiceAPI implements IProofreadingServiceAPI {
    
    private RestTemplate restTemplate = new RestTemplate();

    @Value("${PROOFREADING_SERVICE_API_URL:}")
    private String ProofreadingServiceApiUrl;

    private String[] LanguageCodes;
    private String[] ContentDomains;

    @PostConstruct
    @Scheduled(cron = "${SCHEDULED_PROOFREADING_CONFIG_RESET_CRON}")
    public void resetConfig(){
        LanguageCodes = this.updateLanguageCodes();
        ContentDomains = this.updateContentDomains();
    }

    @Override
    public String[] getLanguageCodes() {
        return LanguageCodes;
    }

    @Override
    public String[] getContentDomains() {
        return ContentDomains;
    }

    @Override
    public ProofreadResponse proofread(PolishRequest proofreadRequest) {
        return restTemplate.postForEntity(ProofreadingServiceApiUrl+"/proofread", proofreadRequest, ProofreadResponse.class).getBody();
    }

    private String[] updateLanguageCodes() {
        try{
            return restTemplate.getForEntity(ProofreadingServiceApiUrl+"/languages", String[].class).getBody();
        }catch (Exception e){
            return new String[0];
        }
    }

    private String[] updateContentDomains() {
        try {
            return restTemplate.getForEntity(ProofreadingServiceApiUrl+"/domains", String[].class).getBody();
        }
        catch (Exception e){
            return new String[0];
        }
    }
}
