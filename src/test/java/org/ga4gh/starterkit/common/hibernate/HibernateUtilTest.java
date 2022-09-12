package org.ga4gh.starterkit.common.hibernate;

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
public class HibernateUtilTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private HibernateUtil hibernateUtil;

    @Autowired
    private LoggingUtil loggingUtil;

    @DataProvider(name = "readEntityObjectCases")
    public Object[][] readEntityObjectCases() {
        return new Object[][] {
            {
                "20556714",
                "Amy",
                "Ellington",
                false
            },
            {
                "30713324",
                "Brian",
                "McKenzie",
                false
            },
            {
                "55555555",
                "William",
                "Ramirez",
                true
            }
        };
    }

    @DataProvider(name = "createEntityObjectCases")
    public Object[][] createEntityObjectCases() {
        return new Object[][] {
            {
                new Student(
                    "12345678",
                    "Nadia",
                    "Murray"
                ),
                true,
                null,
                null
            },
            {
                new Student(
                    "12345678",
                    "Nadia",
                    "Murray"
                ),
                false,
                "EntityExistsException",
                "A(n) Student already exists at id 12345678"
            }
        };
    }

    @DataProvider(name = "updateEntityObjectCases")
    public Object[][] updateEntityObjectCases() {
        return new Object[][] {
            {
                "12345678",
                new Student(
                    "12345678",
                    "Nadia",
                    "Thomas"
                ),
                true,
                null,
                null
            },
            {
                "12345678",
                new Student(
                    "88664422",
                    "Nadia",
                    "Smith"
                ),
                false,
                "EntityMismatchException",
                "Update requested at id 12345678, but new Student has an id of 88664422"
            },
            {
                "99999999",
                new Student(
                    "99999999",
                    "Isaac",
                    "Green"
                ),
                false,
                "EntityDoesntExistException",
                "No Student at id 99999999"
            }
        };
    }

    @DataProvider(name = "deleteEntityObjectCases")
    public Object[][] deleteEntityObjectCases() {
        return new Object[][] {
            {
                "12345678",
                true,
                null,
                null
            },
            {
                "88664422",
                false,
                "EntityDoesntExistException",
                "No Student at id 88664422"
            }
        };
    }

    @Test(dataProvider = "readEntityObjectCases", groups = "read")
    public void testReadEntityObject(String id, String expFirstName, String expLastName, boolean expNull) {
        Assert.assertTrue(hibernateUtil.getConfigured());
        Student student = hibernateUtil.readEntityObject(Student.class, id, false);
        if (expNull) {
            Assert.assertNull(student);
        } else {
            Assert.assertNotNull(student);
            Assert.assertEquals(student.getFirstName(), expFirstName);
            Assert.assertEquals(student.getLastName(), expLastName);
        }
    }

    @Test(dataProvider = "createEntityObjectCases", groups = "create", dependsOnGroups = "read")
    public void testCreateEntityObject(Student newStudent, boolean expSuccess, String expException, String expMessage) {
        try {
            hibernateUtil.createEntityObject(Student.class, newStudent);
            Assert.assertTrue(expSuccess);
            // access the saved student and verify its attributes
            Student savedStudent = hibernateUtil.readEntityObject(Student.class, newStudent.getId(), false);
            Assert.assertEquals(savedStudent.getId(), newStudent.getId());
            Assert.assertEquals(savedStudent.getFirstName(), newStudent.getFirstName());
            Assert.assertEquals(savedStudent.getLastName(), newStudent.getLastName());
        } catch (Exception ex) {
            loggingUtil.error("Exception occurred: " + ex.getMessage());
            Assert.assertFalse(expSuccess);
            Assert.assertEquals(ex.getClass().getSimpleName(), expException);
            Assert.assertEquals(ex.getMessage(), expMessage);
        }
    }

    @Test(dataProvider = "updateEntityObjectCases", groups = "update", dependsOnGroups = "create")
    public void testUpdateEntityObject(String id, Student newStudent, boolean expSuccess, String expException, String expMessage) {
        try {
            hibernateUtil.updateEntityObject(Student.class, id, newStudent);
            Assert.assertTrue(expSuccess);
            // access the saved student and verify its attributes
            Student savedStudent = hibernateUtil.readEntityObject(Student.class, newStudent.getId(), false);
            Assert.assertEquals(savedStudent.getId(), newStudent.getId());
            Assert.assertEquals(savedStudent.getFirstName(), newStudent.getFirstName());
            Assert.assertEquals(savedStudent.getLastName(), newStudent.getLastName());
        } catch (Exception ex) {
            loggingUtil.error("Exception occurred: " + ex.getMessage());
            Assert.assertFalse(expSuccess);
            Assert.assertEquals(ex.getClass().getSimpleName(), expException);
            Assert.assertEquals(ex.getMessage(), expMessage);
        }
    }

    
    @Test(dataProvider = "deleteEntityObjectCases", groups = "delete", dependsOnGroups = "update")
    public void testDeleteEntityObject(String id, boolean expSuccess, String expException, String expMessage) {
        try {
            hibernateUtil.deleteEntityObject(Student.class, id);
            Assert.assertTrue(expSuccess);
            Student savedStudent = hibernateUtil.readEntityObject(Student.class, id, false);
            Assert.assertNull(savedStudent);
        } catch (Exception ex) {
            loggingUtil.error("Exception occurred: " + ex.getMessage());
            Assert.assertFalse(expSuccess);
            Assert.assertEquals(ex.getClass().getSimpleName(), expException);
            Assert.assertEquals(ex.getMessage(), expMessage);
        }
    }
}
