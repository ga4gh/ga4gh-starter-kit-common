package org.ga4gh.starterkit.common.util.logging;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.ga4gh.starterkit.common.config.LogLevel;
import org.ga4gh.starterkit.common.config.ServerProps;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoggingUtilTest {

    private final PrintStream originalStdOut = System.out;
    private ByteArrayOutputStream capturedStdOut = new ByteArrayOutputStream();

    private final String ERROR_AND_HIGHER = "[ERROR] error message";
    private final String WARN_AND_HIGHER = "[WARN] warn message\n" + ERROR_AND_HIGHER;
    private final String INFO_AND_HIGHER = "[INFO] info message\n" + WARN_AND_HIGHER;
    private final String DEBUG_AND_HIGHER = "[DEBUG] debug message\n" + INFO_AND_HIGHER;
    private final String TRACE_AND_HIGHER = "[TRACE] trace message\n" + DEBUG_AND_HIGHER;

    @BeforeMethod
    public void beforeTest() {
        System.setOut(new PrintStream(capturedStdOut));
    }

    @AfterMethod
    public void afterTest() {
        System.setOut(originalStdOut);
        capturedStdOut = new ByteArrayOutputStream();
    }

    @DataProvider(name = "cases")
    public Object[][] getData() {
        return new Object[][] {
            {
                LogLevel.TRACE,
                "test.log",
                TRACE_AND_HIGHER
            },
            {
                LogLevel.DEBUG,
                null,
                DEBUG_AND_HIGHER
            },
            {
                LogLevel.INFO,
                "test.log",
                INFO_AND_HIGHER
            },
            {
                LogLevel.WARN,
                null,
                WARN_AND_HIGHER
            },
            {
                LogLevel.ERROR,
                "test.log",
                ERROR_AND_HIGHER
            }
        };
    }

    @Test(dataProvider = "cases")
    public void testLoggingDefaultProps(LogLevel logLevel, String logFile, String expSanitizedLogOutput) throws Exception {
        ServerProps serverProps = new ServerProps();
        serverProps.setLogLevel(logLevel);
        serverProps.setLogFile(logFile);
        LoggingUtil loggingUtil = new LoggingUtil();
        loggingUtil.setServerProps(serverProps);

        Assert.assertEquals(loggingUtil.getConfigured(), false);
        loggingUtil.buildLogger();
        Assert.assertEquals(loggingUtil.getConfigured(), true);

        loggingUtil.trace("trace message");
        loggingUtil.debug("debug message");
        loggingUtil.info("info message");
        loggingUtil.warn("warn message");
        loggingUtil.error("error message");

        String logOutput = "";

        if (logFile == null) {
            logOutput = capturedStdOut.toString();
        } else {
            Path logFilePath = Paths.get(logFile);
            Assert.assertEquals(capturedStdOut.toString(), "");
            logOutput = new String(Files.readAllBytes(logFilePath));
            Files.deleteIfExists(logFilePath);
        }

        String sanitizedLogOutput = removeTimeStampsFromLogOutput(logOutput);
        Assert.assertEquals(sanitizedLogOutput, expSanitizedLogOutput);
    }

    private String removeTimeStampsFromLogOutput(String logOutput) {
        String[] lines = logOutput.split("\n");
        StringBuffer sanitizedLogOutput = new StringBuffer();
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            String sanitizedString = line.substring(20);
            sanitizedLogOutput.append(sanitizedString + "\n");
        }
        sanitizedLogOutput.deleteCharAt(sanitizedLogOutput.length()-1);
        return sanitizedLogOutput.toString();
    }
}
