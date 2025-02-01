package com.TextPolish.demo.BL.Interfaces;

import com.TextPolish.demo.Model.Request.PolishRequest;
import com.TextPolish.demo.Model.Response.PolishResponse;

public interface ITextPolishBL {
    public PolishResponse forwardRequest(PolishRequest polishRequest);
}
