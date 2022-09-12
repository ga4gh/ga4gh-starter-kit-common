package org.ga4gh.starterkit.common.requesthandler;

import javax.annotation.Resource;
import org.ga4gh.starterkit.common.testutil.Student;
import org.ga4gh.starterkit.common.testutil.TestSpringConfig;
import org.ga4gh.starterkit.common.util.logging.LoggingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@ContextConfiguration(classes = {TestSpringConfig.class})
public class BasicShowRequestHandlerTest extends AbstractTestNGSpringContextTests {

    @Resource
    BasicShowRequestHandler<String, Student> handler;

    @Autowired
    private LoggingUtil loggingUtil;

    @DataProvider(name = "showStudentCases")
    public Object[][] showStudentCases() {
        return new Object[][] {
            {
                "20556714",
                "Amy",
                "Ellington",
                true,
                null,
                null
            },
            {
                "30713324",
                "Brian",
                "McKenzie",
                true,
                null,
                null
            },
            {
                "55555555",
                "William",
                "Ramirez",
                false,
                "ResourceNotFoundException",
                "No Student exists at id 55555555"
            }
        };
    }

    @Test(dataProvider = "showStudentCases")
    public void testShowStudent(String id, String expFirstName, String expLastName, boolean expSuccess, String expException, String expMessage) {
        try {
            Student student = handler.prepare(id).handleRequest();
            Assert.assertTrue(expSuccess);
            Assert.assertEquals(student.getFirstName(), expFirstName);
            Assert.assertEquals(student.getLastName(), expLastName);
        } catch (Exception ex) {
            loggingUtil.error("Exception occurred: " + ex.getMessage());
            Assert.assertFalse(expSuccess);
            Assert.assertEquals(ex.getClass().getSimpleName(), expException);
            Assert.assertEquals(ex.getMessage(), expMessage);
        }
    }
}
