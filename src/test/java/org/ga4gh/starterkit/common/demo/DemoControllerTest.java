package org.ga4gh.starterkit.common.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class DemoControllerTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private WebApplicationContext webAppContext;

    private MockMvc mockMvc;

    @BeforeMethod
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }

    @DataProvider(name = "cases")
    public Object[][] getData() {
        return new Object[][] {
            {
                "/hello-world",
                "Hello, World! You have reached the PUBLIC 'hello-world' endpoint"
            },
            {
                "/admin/hello-world",
                "Hello, World! You have reached the ADMIN 'hello-world' endpoint"
            }
        };
    }

    @Test(dataProvider = "cases")
    public void testDemoControllers(String requestPath, String expResponseBody) throws Exception {
        MvcResult result = mockMvc.perform(get(requestPath)).andExpect(status().isOk()).andReturn();
        String responseBody = result.getResponse().getContentAsString();
        Assert.assertEquals(responseBody, expResponseBody);
    }
}
