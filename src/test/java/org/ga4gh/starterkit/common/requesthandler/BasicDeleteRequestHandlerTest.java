package org.ga4gh.starterkit.common.requesthandler;

import javax.annotation.Resource;

import org.ga4gh.starterkit.common.hibernate.HibernateUtil;
import org.ga4gh.starterkit.common.hibernate.exception.EntityExistsException;
import org.ga4gh.starterkit.common.testutil.Student;
import org.ga4gh.starterkit.common.testutil.TestSpringConfig;
import org.ga4gh.starterkit.common.util.logging.LoggingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@ContextConfiguration(classes = {TestSpringConfig.class})
public class BasicDeleteRequestHandlerTest extends AbstractTestNGSpringContextTests {

    @Autowired
    HibernateUtil hibernateUtil;

    @Autowired
    private LoggingUtil loggingUtil;

    @Resource
    BasicDeleteRequestHandler<String, Student> deleteHandler;

    @BeforeClass
    public void setup() throws EntityExistsException {
        hibernateUtil.createEntityObject(Student.class, new Student("92035326", "Daisy", "Hudson"));
        hibernateUtil.createEntityObject(Student.class, new Student("15841564", "Nettie", "Lucas"));
    }

    @DataProvider(name = "deleteStudentCases")
    public Object[][] deleteStudentCases() {
        return new Object[][] {
            {
                "92035326",
                true,
                null,
                null
            },
            {
                "15841564",
                true,
                null,
                null
            },
            {
                "54229099",
                false,
                "ConflictException",
                "No Student at id 54229099"
            }
        };
    }

    @Test(dataProvider = "deleteStudentCases")
    public void testDeleteStudent(String id, boolean expSuccess, String expException, String expMessage) {
        try {
            Student savedStudent = deleteHandler.prepare(id).handleRequest();
            Assert.assertTrue(expSuccess);
            Assert.assertNull(savedStudent);
        } catch (Exception ex) {
            loggingUtil.error("Exception occurred: " + ex.getMessage());
            Assert.assertFalse(expSuccess);
            Assert.assertEquals(ex.getClass().getSimpleName(), expException);
            Assert.assertEquals(ex.getMessage(), expMessage);
        }
    }
}
