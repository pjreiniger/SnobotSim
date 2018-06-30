
package com.snobot.simulator.jni;

public final class RobotStateSingletonJni extends BaseSimulatorJni
{
    private RobotStateSingletonJni()
    {

    }

    public static native void setDisabled(boolean aDisabled);

    public static native void setAutonomous(boolean aAuto);

    public static native void setTest(boolean aTest);

    public static native double getMatchTime();

    public static native void waitForProgramToStart();

    public static native void waitForNextUpdateLoop(double aUpdatePeriod);

    public static native double getCycleTime();
}
