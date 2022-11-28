package com.banking.account.utils.validate;

import com.banking.account.exception.ValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class PeselValidatorTest {

    @Test
    public void validatePassed() {
        //given
        String validPesel = "94040526666";
        //when
        boolean result = PeselValidator.validate(validPesel);
        //then
        assertTrue(result);
    }

    @Test
    public void validateShouldFailNotValid() {
        //given
        String validPesel = "38021751115";
        //when
        boolean result = PeselValidator.validate(validPesel);
        //then
        assertFalse(result);
    }


    @Test
    public void validateShouldFailNotAdult() {
        //given
        String validPesel = "19240562281";
        //when
        boolean result = PeselValidator.validate(validPesel);
        //then
        assertFalse(result);
    }

    @Test(expected = ValidationException.class)
    public void validateLengthShouldFailed() {
        //given
        String validPesel = "123";
        //when
        boolean result = PeselValidator.validate(validPesel);
        //then
    }
}
