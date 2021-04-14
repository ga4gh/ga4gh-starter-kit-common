package org.ga4gh.starterkit.common.hibernate;

import org.ga4gh.starterkit.common.testutil.Student;
import org.ga4gh.starterkit.common.testutil.TestSpringConfig;
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

    @DataProvider(name = "readEntityObjectCases")
    public Object[][] readEntityObjectCases() {
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

    @DataProvider(name = "createEntityObjectCases")
    public Object[][] createEntityObjectCases() {
        return new Object[][] {
            {
                new Student(
                    "12345678",
                    "Nadia",
                    "Murray"
                )
            },
            {
                new Student(
                    "22446688",
                    "Allen",
                    "Pacheko"
                )
            },
            {
                new Student(
                    "33557799",
                    "David",
                    "Weigel"
                )
            }
        };
    }

    @DataProvider(name = "updateEntityObjectCases")
    public Object[][] updateEntityObjectCases() {
        return new Object[][] {
            {
                "12345678",
                "87654321",
                new Student(
                    "87654321",
                    "Nadia",
                    "Thomas"
                )
            },
            {
                "22446688",
                "88664422",
                new Student(
                    "88664422",
                    "Maurice",
                    "Pacheko"
                )
            },
            {
                "33557799",
                "99775533",
                new Student(
                    "99775533",
                    "David",
                    "Turcotte"
                )
            }
        };
    }

    @DataProvider(name = "deleteEntityObjectCases")
    public Object[][] deleteEntityObjectCases() {
        return new Object[][] {
            {"87654321"},
            {"88664422"},
            {"99775533"}
        };
    }

    @Test(dataProvider = "readEntityObjectCases", groups = "read")
    public void testReadEntityObject(String id, String expFirstName, String expLastName) {
        Assert.assertTrue(hibernateUtil.getConfigured());
        Student student = hibernateUtil.readEntityObject(Student.class, id, false);
        Assert.assertEquals(student.getFirstName(), expFirstName);
        Assert.assertEquals(student.getLastName(), expLastName);
    }

    @Test(dataProvider = "createEntityObjectCases", groups = "create", dependsOnGroups = "read")
    public void testCreateEntityObject(Student newStudent) {
        // assert the student doesn't currently exist
        Student savedStudent = hibernateUtil.readEntityObject(Student.class, newStudent.getId(), false);
        Assert.assertNull(savedStudent);
        // create the student in the db
        hibernateUtil.createEntityObject(Student.class, newStudent);
        // access the saved student and verify its attributes
        savedStudent = hibernateUtil.readEntityObject(Student.class, newStudent.getId(), false);
        Assert.assertEquals(savedStudent.getId(), newStudent.getId());
        Assert.assertEquals(savedStudent.getFirstName(), newStudent.getFirstName());
        Assert.assertEquals(savedStudent.getLastName(), newStudent.getLastName());
    }

    @Test(dataProvider = "updateEntityObjectCases", groups = "update", dependsOnGroups = "create")
    public void testUpdateEntityObject(String oldId, String newId, Student newStudentProps) {
        // assert a student exists at the oldId
        Student savedStudentOldId = hibernateUtil.readEntityObject(Student.class, oldId, false);
        Assert.assertNotNull(savedStudentOldId);
        // update the student in the db
        hibernateUtil.updateEntityObject(Student.class, oldId, newId, newStudentProps);
        // assert a student doesn't exist at the oldId
        savedStudentOldId = hibernateUtil.readEntityObject(Student.class, oldId, false);
        // assert the updated student attributes
        Student savedStudentNewId = hibernateUtil.readEntityObject(Student.class, newId, false);
        Assert.assertEquals(savedStudentNewId.getId(), newId);
        Assert.assertEquals(savedStudentNewId.getFirstName(), newStudentProps.getFirstName());
        Assert.assertEquals(savedStudentNewId.getLastName(), newStudentProps.getLastName());
    }

    @Test(dataProvider = "deleteEntityObjectCases", groups = "delete", dependsOnGroups = "update")
    public void testDeleteEntityObject(String id) {
        // assert a student exists at the id
        Student savedStudent = hibernateUtil.readEntityObject(Student.class, id, false);
        Assert.assertNotNull(savedStudent);
        // delete the student
        hibernateUtil.deleteEntityObject(Student.class, id);
        // assert student no longer exists at the id
        savedStudent = hibernateUtil.readEntityObject(Student.class, id, false);
        Assert.assertNull(savedStudent);
    }
}
