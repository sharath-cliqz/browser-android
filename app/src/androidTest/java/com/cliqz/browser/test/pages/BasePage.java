package com.cliqz.browser.test.pages;

import com.cliqz.browser.test.driver.NativeDriver;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;

import java.io.File;
import java.io.IOException;

/**
 * Copyright © Cliqz 2019
 */
public class BasePage {

    public static NativeDriver driver;

    @Rule
    public TestName testName = new TestName();

    @BeforeClass
    public static void setUpClass() {
        driver = new NativeDriver();
    }

    @Before
    public void setUp() throws Exception {
        driver.launchActivity();
        driver.sleep(1); // Cool down after the Activity is launched
    }

    @After
    public void tearDown() {
        driver.takeScreenshot(testName.getMethodName()
                + "_TestEndScreenshot");
    }

    public void clearData() {
        try {
            File[] appDataFiles = driver.targetContext.getFilesDir().listFiles();
            if (appDataFiles != null) {
                for (File dataFile : appDataFiles) {
                    Runtime.getRuntime().exec("rm -rf " + dataFile.toString());
                }
            }
        } catch(IOException ioe) {
            driver.log(ioe.toString());
        }
    }
}
