package com.TextPolish.demo.API.Controllers;

import com.TextPolish.demo.BL.Interfaces.ITextPolishBL;
import com.TextPolish.demo.Model.Request.PolishRequest;
import com.TextPolish.demo.Model.Response.PolishResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/TextPolish")
public class TextPolishController {

    private final ITextPolishBL externalCommunication;

    @PostMapping("/polish")
    public PolishResponse polish(@RequestBody @Valid PolishRequest dto) {
        return externalCommunication.forwardRequest(dto);
    }
}
