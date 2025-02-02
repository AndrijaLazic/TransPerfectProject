package com.TextPolish.demo.BL.Implementations;

import com.TextPolish.demo.BL.Interfaces.ITextPolishBL;
import com.TextPolish.demo.ExternalServicesBL.Interface.IProofreadingServiceAPI;
import com.TextPolish.demo.Model.Request.PolishRequest;
import com.TextPolish.demo.Model.Response.PolishResponse;
import com.TextPolish.demo.Model.Response.ProofreadResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import org.springframework.stereotype.Service;

import java.util.Stack;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class TextPolishBL implements ITextPolishBL {
    private final IProofreadingServiceAPI proofreadingServiceAPI;

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
        Stack<Integer> stack = new Stack<>();
        StringBuilder stringBuilder = new StringBuilder(content);
        char[] characters = content.toCharArray();
        int charLength = characters.length;

        int difference = 0;

        for (int i = 0; i < charLength; i++) {
            if(characters[i] == '<'){
                stack.push(i);
            } else if (!stack.empty() && characters[i] == '/'
                    && i+1 < charLength && characters[i+1] == '>') {
                    int leftIndex = stack.pop();
                    int rightIndex = i+2;
                    stringBuilder.delete(leftIndex - difference, rightIndex - difference);
                    difference += rightIndex - leftIndex;
            }
        }
        return stringBuilder.toString();
    }
}
