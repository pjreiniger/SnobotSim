package com.snobot.simulator.cpp_wrapper;

public final class CppJniWrapper
{
    private CppJniWrapper()
    {

    }

    public static String getLibraryName()
    {
        return "CppTest";
    }

    public static void createRobot()
    {
        throw new RuntimeException("Not Supported");
    }

    public static void startCompetition()
    {
        throw new RuntimeException("Not Supported");
    }
}
