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

    public static native void createRobot();

    public static native void startCompetition();
}
