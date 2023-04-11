package com.example.coffeetrail;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.coffeetrail.model.UserAccount;

class ValidatePasswordTest {
    @Test
    public void passwordValidator_PasswordSimple_ReturnsTrue() {
        assertTrue(UserAccount.isValidPassword("Hello123"));
        assertTrue(UserAccount.isValidPassword("s4G2sc153"));
        assertTrue(UserAccount.isValidPassword("v8sDB3241"));
        assertTrue(UserAccount.isValidPassword("Correct23sbr"));
        assertTrue(UserAccount.isValidPassword("numberZ34as"));
        assertTrue(UserAccount.isValidPassword("@abeBF42!!"));
    }

    @Test
    public void passwordValidator_PasswordSimple_ReturnsFalse() {
        assertFalse(UserAccount.isValidPassword("hello12"));
        assertFalse(UserAccount.isValidPassword("no"));
        assertFalse(UserAccount.isValidPassword("HI12345"));
        assertFalse(UserAccount.isValidPassword("Hello"));
        assertFalse(UserAccount.isValidPassword("Hello123"));


    }

    @Test
    public void passwordValidator_Empty_ReturnsFalse() {
        assertFalse(UserAccount.isValidPassword(""));
    }
}
