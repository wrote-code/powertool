package com.wrotecode.powertool.utils;

import org.junit.Assert;
import org.junit.Test;

public class FileUtilsTest {
    @Test
    public void testCopyFile() throws Exception {
        FileUtils.copyFile("pom.xml", "logs/pom.xml");
    }

    @Test
    public void testMkdir() {
        boolean result = FileUtils.mkdir("logs", "hello");
        Assert.assertTrue(result);
    }
}
