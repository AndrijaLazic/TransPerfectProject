package com.TextPolish.demo.BL.Implementations;

import com.TextPolish.demo.BL.Interfaces.ITextPolishBL;
import com.TextPolish.demo.ExternalServicesBL.Interface.IProofreadingServiceAPI;
import com.TextPolish.demo.Model.Request.PolishRequest;
import com.TextPolish.demo.Model.Response.PolishResponse;
import com.TextPolish.demo.Model.Response.ProofreadResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import org.springframework.stereotype.Service;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class TextPolishBL implements ITextPolishBL {
    private final IProofreadingServiceAPI proofreadingServiceAPI;
    private static Pattern tagRegex = Pattern.compile("<[^>]*>");

    public PolishResponse forwardRequest(PolishRequest polishRequest) {

        polishRequest.setContent(removeTags(polishRequest.getContent()));

        ProofreadResponse proofreadResponse = proofreadingServiceAPI.proofread(polishRequest);
        PolishResponse response = new PolishResponse();
        response.polishedContent = proofreadResponse.PolishedContent;

        JaroWinklerSimilarity jaroWinklerSimilarity = new JaroWinklerSimilarity();
        response.similarity = jaroWinklerSimilarity.apply(polishRequest.getContent(), response.polishedContent);

        return response;
    }

    private String removeTags(String content) {
        return tagRegex.matcher(content).replaceAll("");
    }
}
