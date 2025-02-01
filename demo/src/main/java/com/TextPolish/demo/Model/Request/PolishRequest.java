package com.TextPolish.demo.Model.Request;

import com.TextPolish.demo.Model.Validators.IsValidContentSize;
import com.TextPolish.demo.Model.Validators.IsValidDomain;
import com.TextPolish.demo.Model.Validators.IsValidLanguage;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolishRequest {
    @NotNull
    @NotEmpty
    @Size(max = 20)
    @IsValidLanguage
    private String Language;

    @NotNull
    @Size(max = 20)
    @NotEmpty
    @IsValidDomain
    private String Domain;

    @NotNull
    @NotEmpty
    @IsValidContentSize
    private String Content;
}
