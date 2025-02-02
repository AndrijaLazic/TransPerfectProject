package com.TextPolish.demo.API.Controllers;

import com.TextPolish.demo.ExternalServicesBL.Interface.IProofreadingServiceAPI;
import com.TextPolish.demo.Model.Request.PolishRequest;
import com.TextPolish.demo.Model.Response.ProofreadResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class TextPolishControllerTest {

    @MockitoBean
    private IProofreadingServiceAPI proofreadingServiceAPI;

    @Autowired
    private TextPolishController textPolishController;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        ProofreadResponse proofreadResponse = new ProofreadResponse();
        proofreadResponse.PolishedContent = "I hope i get hired";
        when(proofreadingServiceAPI.proofread(any())).thenReturn(proofreadResponse);
        doNothing().when(proofreadingServiceAPI).resetConfig();
        when(proofreadingServiceAPI.getLanguageCodes()).thenReturn(new String[]{"en-US", "en-GB", "fr-FR", "de-DE"});
        when(proofreadingServiceAPI.getContentDomains()).thenReturn(new String[]{"academic", "business", "general", "casual", "creative"});
    }


    @Test
    void polish_success() throws Exception {

        PolishRequest polishRequest = new PolishRequest();
        polishRequest.setLanguage("en-US");
        polishRequest.setDomain("academic");
        polishRequest.setContent("I hope i get hired");

        String jsonRequest = new ObjectMapper().writeValueAsString(polishRequest);

        var res = mvc.perform(MockMvcRequestBuilders.post("/TextPolish/polish")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest));

    }

    @Test
    void polish_whenContentHasTags_success() throws Exception {

        PolishRequest polishRequest = new PolishRequest();
        polishRequest.setLanguage("en-US");
        polishRequest.setDomain("academic");
        polishRequest.setContent("<mark type=\"bold\" size=\"13\"/>I hope i get hired <mark type=\"bold\" size=\"13\"/><mark type=\"bold\" size=\"13\"/>");

        String jsonRequest = new ObjectMapper().writeValueAsString(polishRequest);

        var res = mvc.perform(MockMvcRequestBuilders.post("/TextPolish/polish")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest));
    }

    @Test
    void polish_whenInvalidLanguage_fail() throws Exception {

        PolishRequest polishRequest = new PolishRequest();
        polishRequest.setLanguage("FakeLanguage");
        polishRequest.setDomain("academic");
        polishRequest.setContent("I hope i get hired");

        String jsonRequest = new ObjectMapper().writeValueAsString(polishRequest);

        mvc.perform(MockMvcRequestBuilders.post("/TextPolish/polish")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void polish_whenInvalidDomain_fail() throws Exception {

        PolishRequest polishRequest = new PolishRequest();
        polishRequest.setLanguage("en-US");
        polishRequest.setDomain("BadDomain");
        polishRequest.setContent("I hope i get hired");

        String jsonRequest = new ObjectMapper().writeValueAsString(polishRequest);

        mvc.perform(MockMvcRequestBuilders.post("/TextPolish/polish")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void polish_whenInvalidContentCount_fail() throws Exception {

        PolishRequest polishRequest = new PolishRequest();
        polishRequest.setLanguage("en-US");
        polishRequest.setDomain("academic");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            builder.append("I hope i get hired ");
        }
        builder.append("I hope i get hired ");

        polishRequest.setContent(builder.toString());

        String jsonRequest = new ObjectMapper().writeValueAsString(polishRequest);

        mvc.perform(MockMvcRequestBuilders.post("/TextPolish/polish")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().is4xxClientError());
    }


}