package org.ga4gh.starterkit.common.requesthandler;

import javax.annotation.Resource;
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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test
@ContextConfiguration(classes = {TestSpringConfig.class})
public class BasicCreateRequestHandlerTest extends AbstractTestNGSpringContextTests {

    @Autowired
    HibernateUtil hibernateUtil;

    @Resource
    BasicCreateRequestHandler<String, Student> createHandler;

    @AfterClass
    public void cleanUp() throws EntityExistsException, EntityDoesntExistException {
        hibernateUtil.deleteEntityObject(Student.class, "30076991");
        hibernateUtil.deleteEntityObject(Student.class, "76658204");
        hibernateUtil.deleteEntityObject(Student.class, "45850321");
    }

    @DataProvider(name = "createStudentCases")
    public Object[][] createStudentCases() {
        return new Object[][] {
            {
                new Student(
                    "30076991",
                    "Lydia",
                    "Carlson"
                ),
                true,
                null,
                null
            },
            {
                new Student(
                    "30076991",
                    "Lydia",
                    "Carlson"
                ),
                false,
                "ConflictException",
                "A(n) Student already exists at id 30076991"
            },
            {
                new Student(
                    "76658204",
                    "Hattie",
                    "Gordon"
                ),
                true,
                null,
                null,
            },
            {
                new Student(
                    "76658204",
                    "Hattie",
                    "Gordon"
                ),
                false,
                "ConflictException",
                "A(n) Student already exists at id 76658204",
            },
            {
                new Student(
                    null,
                    "Norman",
                    "Grant"
                ),
                false,
                "IllegalArgumentException",
                "id to load is required for loading"
            },
            {
                new Student(
                    "45850321",
                    "Jacquelyn",
                    "Green"
                ),
                true,
                null,
                null
            }
        };
    }

    @Test(dataProvider = "createStudentCases")
    public void testCreateStudent(Student newStudent, boolean expSuccess, String expException, String expMessage) {
        // create the student and assert properties match
        try {
            Student savedStudent = createHandler.prepare(newStudent).handleRequest();
            Assert.assertTrue(expSuccess);
            Assert.assertNotNull(savedStudent);
            Assert.assertEquals(savedStudent.getFirstName(), newStudent.getFirstName());
            Assert.assertEquals(savedStudent.getLastName(), newStudent.getLastName());
        } catch (Exception ex) {
            Assert.assertFalse(expSuccess);
            Assert.assertEquals(ex.getClass().getSimpleName(), expException);
            Assert.assertEquals(ex.getMessage(), expMessage);
        }
    }
}
