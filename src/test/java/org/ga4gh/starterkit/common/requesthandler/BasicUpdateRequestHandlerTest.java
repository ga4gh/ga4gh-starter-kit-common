package org.ga4gh.starterkit.common.requesthandler;

import jakarta.annotation.Resource;
import org.ga4gh.starterkit.common.hibernate.HibernateUtil;
import org.ga4gh.starterkit.common.hibernate.exception.EntityDoesntExistException;
import org.ga4gh.starterkit.common.hibernate.exception.EntityExistsException;
import org.ga4gh.starterkit.common.testutil.Student;
import org.ga4gh.starterkit.common.testutil.TestSpringConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@ContextConfiguration(classes = {TestSpringConfig.class})
public class BasicUpdateRequestHandlerTest extends AbstractTestNGSpringContextTests {

    @Autowired
    HibernateUtil hibernateUtil;

    @Resource
    BasicUpdateRequestHandler<String, Student> updateHandler;

    @BeforeClass
    public void setup() throws EntityExistsException {
        hibernateUtil.createEntityObject(Student.class, new Student("79982061", "Ervin", "Ryan"));
        hibernateUtil.createEntityObject(Student.class, new Student("74533654", "Clinton", "Ross"));
        hibernateUtil.createEntityObject(Student.class, new Student("34298392", "Alejandro", "Ross"));
    }

    @AfterClass
    public void cleanup() throws EntityDoesntExistException, EntityExistsException {
        hibernateUtil.deleteEntityObject(Student.class, "79982061");
        hibernateUtil.deleteEntityObject(Student.class, "74533654");
        hibernateUtil.deleteEntityObject(Student.class, "34298392");
    }

    @DataProvider(name = "updateStudentCases")
    public Object[][] updateStudentCases() {
        return new Object[][] {
            {
                "79982061",
                new Student(
                    "79982061",
                    "Ervin",
                    "Walsh"
                ),
                true,
                null,
                null
            },
            {
                "74533654",
                new Student(
                    "74533654",
                    "Ida",
                    "Little"
                ),
                true,
                null,
                null
            },
            {
                "11111111",
                new Student(
                    "11111111",
                    "Ida",
                    "Little"
                ),
                false,
                "ConflictException",
                "No Student at id 11111111"
            },
            {
                "34298392",
                new Student(
                    "11111111",
                    "Ida",
                    "Little"
                ),
                false,
                "BadRequestException",
                "Update requested at id 34298392, but new Student has an id of 11111111"
            }
        };
    }

    @Test(dataProvider = "updateStudentCases")
    public void testUpdateStudent(String id, Student newStudent, boolean expSuccess, String expException, String expMessage) {

        try {
            Student updatedStudent = updateHandler.prepare(id, newStudent).handleRequest();
            Assert.assertTrue(expSuccess);
            Assert.assertEquals(updatedStudent.getId(), newStudent.getId());
            Assert.assertEquals(updatedStudent.getFirstName(), newStudent.getFirstName());
            Assert.assertEquals(updatedStudent.getLastName(), newStudent.getLastName());
        } catch (Exception ex) {
            Assert.assertFalse(expSuccess);
            Assert.assertEquals(ex.getClass().getSimpleName(), expException);
            Assert.assertEquals(ex.getMessage(), expMessage);
        }
    }
}
