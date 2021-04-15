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
public class BasicCreateRequestHandlerTest extends AbstractTestNGSpringContextTests {

    @Resource
    BasicShowRequestHandler<String, Student> showHandler;

    @Resource
    BasicCreateRequestHandler<String, Student> createHandler;

    @Resource
    BasicDeleteRequestHandler<String, Student> deleteHandler;

    @DataProvider(name = "createStudentCases")
    public Object[][] createStudentCases() {
        return new Object[][] {
            {
                new Student(
                    "30076991",
                    "Lydia",
                    "Carlson"
                )
            },
            {
                new Student(
                    "76658204",
                    "Hattie",
                    "Gordon"
                )
            },
            {
                new Student(
                    "45850321",
                    "Jacquelyn",
                    "Green"
                )
            }
        };
    }

    @Test(dataProvider = "createStudentCases")
    public void testCreateStudent(Student newStudent) {
        // assert no student exists yet
        Student savedStudent = showHandler.prepare(newStudent.getId()).handleRequest();
        Assert.assertNull(savedStudent);

        // create the student and assert properties match
        savedStudent = createHandler.prepare(newStudent).handleRequest();
        Assert.assertEquals(savedStudent.getFirstName(), newStudent.getFirstName());
        Assert.assertEquals(savedStudent.getLastName(), newStudent.getLastName());

        // delete the student
        deleteHandler.prepare(newStudent.getId()).handleRequest();
    }
}
