package com.apon.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for MessageResource. There is no need to extend BaseTest, since MessageResource only contains static functions.
 */
public class TestMessageResource {

    @Test
    public void getValue() {
        // Assumes that the test values are contained inside MessageResource.
        // Case 1 without arguments.
        String key1 = "TestMessageResource.test";
        String value1 = "Dit is een test.";
        assertEquals(MessageResource.getInstance().getValue(key1), value1);

        // Case 2 with single argument.
        String key2 = "TestMessageResource.withArguments";
        String value2 = "Dit is een test voor Rico.";
        assertEquals(MessageResource.getInstance().getValue(key2, "Rico"), value2);
    }
}
