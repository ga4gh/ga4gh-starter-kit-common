package org.ga4gh.starterkit.common.exception;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ResourceNotFoundExceptionTest {

    private enum ConstructorType {SIMPLE, MESSAGE, CAUSE}

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
                "no resource by that id: '12345'",
                null,
                "no resource by that id: '12345'"
            },
            {
                ConstructorType.CAUSE,
                null,
                new Throwable("no resource by that id: '98765'"),
                "java.lang.Throwable: no resource by that id: '98765'"
            }
        };
    }

    @Test(dataProvider = "cases")
    public void testResourceNotFoundException(ConstructorType constructorType, String message, Throwable cause, String expMessage) {
        try {
            switch (constructorType) {
                case SIMPLE:
                    throw new ResourceNotFoundException();
                case MESSAGE:
                    throw new ResourceNotFoundException(message);
                case CAUSE:
                    throw new ResourceNotFoundException(cause);
            }
        } catch (ResourceNotFoundException ex) {
            Assert.assertEquals(ex.getMessage(), expMessage);
        }
    }
}
