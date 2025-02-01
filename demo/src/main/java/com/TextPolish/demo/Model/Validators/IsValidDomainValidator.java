package com.TextPolish.demo.Model.Validators;

import com.TextPolish.demo.ExternalServicesBL.Implementation.ProofreadingServiceAPI;
import com.TextPolish.demo.ExternalServicesBL.Interface.IProofreadingServiceAPI;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IsValidDomainValidator implements ConstraintValidator<IsValidDomain,String> {

    private final IProofreadingServiceAPI proofreadingServiceAPI;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null || value.isEmpty())
            return false;
        String[] domains = proofreadingServiceAPI.getContentDomains();
        if(domains == null || domains.length == 0)
            return false;
        for (int i = 0; i < domains.length; i++) {
            if (domains[i].equals(value)) {
                return true;
            }
        }
        return false;
    }
}
