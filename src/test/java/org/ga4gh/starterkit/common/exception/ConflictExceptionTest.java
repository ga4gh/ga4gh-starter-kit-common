package org.ga4gh.starterkit.common.exception;

import org.ga4gh.starterkit.common.util.logging.LoggingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ConflictExceptionTest {

    private enum ConstructorType {SIMPLE, MESSAGE, CAUSE}

    @Autowired
    private LoggingUtil loggingUtil;

    @DataProvider(name = "cases")
    public Object[][] getData() {
        return new Object[][] {
            {
                ConstructorType.SIMPLE,
                null,
                null,
                null
            },
            {
                ConstructorType.MESSAGE,
                "invalid query parameter, 'expand'",
                null,
                "invalid query parameter, 'expand'"
            },
            {
                ConstructorType.CAUSE,
                null,
                new Throwable("invalid query parameter, 'collapse'"),
                "java.lang.Throwable: invalid query parameter, 'collapse'"
            }
        };
    }

    @Test(dataProvider = "cases")
    public void testConflictException(ConstructorType constructorType, String message, Throwable cause, String expMessage) {
        try {
            switch (constructorType) {
                case SIMPLE:
                    throw new ConflictException();
                case MESSAGE:
                    throw new ConflictException(message);
                case CAUSE:
                    throw new ConflictException(cause);
            }
        } catch (ConflictException ex) {
            loggingUtil.error("Exception occurred: " + ex.getMessage());
            Assert.assertEquals(ex.getMessage(), expMessage);
        }
    }
}
