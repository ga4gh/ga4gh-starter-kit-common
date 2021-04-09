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
    public Object[][] getReadEntityObjectCases() {
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

    @Test(dataProvider = "readEntityObjectCases")
    public void testReadEntityObject(String id, String expFirstName, String expLastName) {
        Assert.assertTrue(hibernateUtil.getConfigured());
        Student student = hibernateUtil.readEntityObject(Student.class, id, false);
        Assert.assertEquals(student.getFirstName(), expFirstName);
        Assert.assertEquals(student.getLastName(), expLastName);
    }
}
