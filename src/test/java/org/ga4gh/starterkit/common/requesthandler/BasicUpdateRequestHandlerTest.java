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
public class BasicUpdateRequestHandlerTest extends AbstractTestNGSpringContextTests {

    @Resource
    BasicShowRequestHandler<String, Student> showHandler;

    @Resource
    BasicCreateRequestHandler<String, Student> createHandler;

    @Resource
    BasicUpdateRequestHandler<String, Student> updateHandler;

    @Resource
    BasicDeleteRequestHandler<String, Student> deleteHandler;

    @DataProvider(name = "updateStudentCases")
    public Object[][] updateStudentCases() {
        return new Object[][] {
            {
                new Student(
                    "79982061",
                    "Ervin",
                    "Ryan"
                ),
                new Student(
                    "12586609",
                    "Ervin",
                    "Walsh"
                )
            },
            {
                new Student(
                    "74533654",
                    "Clinton",
                    "Ross"
                ),
                new Student(
                    "34298392",
                    "Alejandro",
                    "Ross"
                )
            },
            {
                new Student(
                    "18200782",
                    "Ida",
                    "Little"
                ),
                new Student(
                    "18200782",
                    "Viola",
                    "Lawrence"
                )
            }
        };
    }

    @Test(dataProvider = "updateStudentCases")
    public void testUpdateStudent(Student oldStudent, Student newStudent) {
        String oldId = oldStudent.getId();
        String newId = newStudent.getId();

        // assert no student exists at the old or new id
        Student savedOldStudent = showHandler.prepare(oldId).handleRequest();
        Student savedNewStudent = showHandler.prepare(newId).handleRequest();
        Assert.assertNull(savedOldStudent);
        Assert.assertNull(savedNewStudent);

        // create the student at the old id and assert it exists
        savedOldStudent = createHandler.prepare(oldStudent).handleRequest();
        Assert.assertNotNull(savedOldStudent);

        // update the old student with the new student and assert it exists
        savedNewStudent = updateHandler.prepare(oldId, newStudent).handleRequest();
        Assert.assertNotNull(savedNewStudent);

        // assert the properties of the new student
        Assert.assertEquals(savedNewStudent.getFirstName(), newStudent.getFirstName());
        Assert.assertEquals(savedNewStudent.getLastName(), newStudent.getLastName());

        // if the id has been updated, assert nothing exists at the old id
        if (!oldId.equals(newId)) {
            savedOldStudent = showHandler.prepare(oldId).handleRequest();
            Assert.assertNull(savedOldStudent);
        }

        // delete the student
        deleteHandler.prepare(newId).handleRequest();
    }
}
