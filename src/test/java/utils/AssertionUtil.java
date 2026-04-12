package utils;

import org.testng.Assert;

public class AssertionUtil {
    
    public static void verifyTrue(boolean condition, String message) {
        Assert.assertTrue(condition, message);  
    }
    
    public static void verifyEquals(String actual, String expected) {
        Assert.assertEquals(actual, expected);
    }
}