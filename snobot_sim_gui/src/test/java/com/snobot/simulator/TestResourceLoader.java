package com.snobot.simulator;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.snobot.test.utilities.BaseSimulatorTest;

public class TestResourceLoader extends BaseSimulatorTest
{
    @Test
    public void testLoadMissingResource()
    {
        Assertions.assertFalse(JniLibraryResourceLoader.loadLibrary("DoesNotExist"));
    }

    @Test
    public void testLoadLibraryTwice()
    {
        Assertions.assertTrue(JniLibraryResourceLoader.loadLibrary("wpiHal"));
        Assertions.assertTrue(JniLibraryResourceLoader.loadLibrary("wpiHal"));
    }

    @Test
    public void testCopyResource() throws IOException
    {
        String resourceFile = "/com/snobot/simulator/yeti_art.txt";
        Assertions.assertTrue(JniLibraryResourceLoader.copyResourceFromJar(resourceFile, new File("test_output/deleteOnCopy.properties")));
        Assertions.assertTrue(JniLibraryResourceLoader.copyResourceFromJar(resourceFile, new File("test_output/saveOnCopy.properties"), false));
    }
}
