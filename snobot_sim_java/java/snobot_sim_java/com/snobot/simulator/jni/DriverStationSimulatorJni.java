package com.snobot.simulator.jni;

public class DriverStationSimulatorJni extends BaseSnobotJni
{
    public static native void setEnabled(boolean aEnabled);

    public static native void setAutonomous(boolean aAuton);

    public static native void waitForProgramToStart();

    public static native void delayForNextUpdateLoop(double aWaitPeriod);

    public static native double getMatchTime();

    public static native void setJoystickInformation(int aJoystickHandle, float[] aAxesArray, short[] aPovsArray, int aButtonCount, int aButtonMask);

    public static native void setMatchInfo(String eventName, int matchTypeVal, int matchNumber, int replayNumber,
            String gameSpecificMessage);

}
