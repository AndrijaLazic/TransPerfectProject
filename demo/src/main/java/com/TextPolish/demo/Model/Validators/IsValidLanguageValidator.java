package com.TextPolish.demo.Model.Validators;

import com.TextPolish.demo.ExternalServicesBL.Implementation.ProofreadingServiceAPI;
import com.TextPolish.demo.ExternalServicesBL.Interface.IProofreadingServiceAPI;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IsValidLanguageValidator implements ConstraintValidator<IsValidLanguage,String> {

    private final IProofreadingServiceAPI proofreadingServiceAPI;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null || value.isEmpty())
            return false;
        String[] languages = proofreadingServiceAPI.getLanguageCodes();
        if(languages == null || languages.length == 0)
            return false;

        for (int i = 0; i < languages.length; i++) {
            if (languages[i].equals(value)) {
                return true;
            }
        }
        return false;
    }
}
