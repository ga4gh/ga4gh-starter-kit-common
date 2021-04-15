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
public class BasicDeleteRequestHandlerTest extends AbstractTestNGSpringContextTests {

    @Resource
    BasicShowRequestHandler<String, Student> showHandler;

    @Resource
    BasicCreateRequestHandler<String, Student> createHandler;

    @Resource
    BasicDeleteRequestHandler<String, Student> deleteHandler;

    @DataProvider(name = "deleteStudentCases")
    public Object[][] deleteStudentCases() {
        return new Object[][] {
            {
                new Student(
                    "92035326",
                    "Daisy",
                    "Hudson"
                )
            },
            {
                new Student(
                    "15841564",
                    "Nettie",
                    "Lucas"
                )
            },
            {
                new Student(
                    "54229099",
                    "Gordon",
                    "Mathis"
                )
            }
        };
    }

    @Test(dataProvider = "deleteStudentCases")
    public void testDeleteStudent(Student newStudent) {
        // assert no student exists yet
        Student savedStudent = showHandler.prepare(newStudent.getId()).handleRequest();
        Assert.assertNull(savedStudent);

        // create the student and assert not null
        savedStudent = createHandler.prepare(newStudent).handleRequest();
        Assert.assertNotNull(savedStudent);

        // delete the student and assert it no longer exists
        savedStudent = deleteHandler.prepare(newStudent.getId()).handleRequest();
        Assert.assertNull(savedStudent);
        savedStudent = showHandler.prepare(newStudent.getId()).handleRequest();
        Assert.assertNull(savedStudent);
    }
}
