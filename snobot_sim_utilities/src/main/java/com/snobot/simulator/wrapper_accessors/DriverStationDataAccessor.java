package com.snobot.simulator.wrapper_accessors;

public interface DriverStationDataAccessor
{

    public static final double sDEFAULT_LOOP_PERIOD = .02;

    void setDisabled(boolean aDisabled);

    void setAutonomous(boolean aAutonomous);

    double getMatchTime();

    void waitForProgramToStart();

    /**
     * Simulates waiting for a DS packet to come in. Actually waits the amount
     * of time. Uses default tactical timing of 20ms/50hz
     */
    default void waitForNextUpdateLoop()
    {
        waitForNextUpdateLoop(sDEFAULT_LOOP_PERIOD);
    }

    /**
     * Simulates waiting for a DS packet to come in. Actually waits the amount
     * of time
     *
     * @param aUpdatePeriod
     *            The time, in seconds, to pause before notifying the DS it has
     *            received data
     */
    void waitForNextUpdateLoop(double aUpdatePeriod);

    enum MatchType
    {
        None, Practice, Qualification, Elimination
    }

    void setMatchInfo(String aEventName, MatchType aMatchType, int aMatchNumber, int aReplayNumber, String aGameSpecificMessage);

    void setJoystickInformation(int aJoystick, float[] aAxisValues, short[] aPovValues, int aButtonCount, int aButtonMask);

    double getTimeSinceEnabled();
}
