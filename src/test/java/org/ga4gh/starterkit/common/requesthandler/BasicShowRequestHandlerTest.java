package org.ga4gh.starterkit.common.requesthandler;

import javax.annotation.Resource;
import org.ga4gh.starterkit.common.testutil.Student;
import org.ga4gh.starterkit.common.testutil.TestSpringConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@ContextConfiguration(classes = {TestSpringConfig.class})
public class BasicShowRequestHandlerTest extends AbstractTestNGSpringContextTests {

    @Resource
    BasicShowRequestHandler<String, Student> handler;

    @DataProvider(name = "showStudentCases")
    public Object[][] showStudentCases() {
        return new Object[][] {
            {
                "20556714",
                "Amy",
                "Ellington"
            },
            {
                "30713324",
                "Brian",
                "McKenzie"
            },
            {
                "41576881",
                "William",
                "Ramirez"
            }
        };
    }

    @Test(dataProvider = "showStudentCases")
    public void testShowStudent(String id, String expFirstName, String expLastName) {
        Student student = handler.prepare(id).handleRequest();
        Assert.assertEquals(student.getFirstName(), expFirstName);
        Assert.assertEquals(student.getLastName(), expLastName);
    }
}
