package com.nnk.springboot.validators;

import com.nnk.springboot.domain.BidList;
import org.apache.commons.validator.routines.DoubleValidator;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class BidListValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return BidList.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        BidList bidList = (BidList) o;
        DoubleValidator doubleValidator = new DoubleValidator();
        if (bidList.getAccount() == null || bidList.getAccount().isEmpty()) {
            errors.rejectValue("account", "account.empty", "Account is mandatory");
        }

        if (bidList.getType() == null || bidList.getType().isEmpty()) {
            errors.rejectValue("type", "type.empty", "Type is mandatory");
        }

        if (bidList.getBidQuantity() == null || !doubleValidator.minValue(bidList.getBidQuantity(), 0)) {
            errors.rejectValue("bidQuantity", "wrongValue", "bidQuantity must be positive");
        }
    }
}
