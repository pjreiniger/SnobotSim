package com.snobot.simulator.wrapper_accessors.jni;

import com.snobot.simulator.jni.JoystickJni;
import com.snobot.simulator.jni.RobotStateSingletonJni;
import com.snobot.simulator.wrapper_accessors.DriverStationDataAccessor;

public class JniDriverStationWrapperAccessor implements DriverStationDataAccessor
{

    @Override
    public void setDisabled(boolean aDisabled)
    {
        RobotStateSingletonJni.setDisabled(aDisabled);
    }

    @Override
    public void setAutonomous(boolean aAuton)
    {
        RobotStateSingletonJni.setAutonomous(aAuton);
    }

    @Override
    public double getMatchTime()
    {
        return RobotStateSingletonJni.getMatchTime();
    }

    @Override
    public void waitForProgramToStart()
    {
        RobotStateSingletonJni.waitForProgramToStart();
    }

    @Override
    public void waitForNextUpdateLoop(double aUpdatePeriod)
    {
        RobotStateSingletonJni.waitForNextUpdateLoop(aUpdatePeriod);
    }

    @Override
    public void setJoystickInformation(int aIndex, float[] aAxisValues, short[] aPovValues, int aButtonCount, int aButtonMask)
    {
        JoystickJni.setJoystickInformation(aIndex, aAxisValues, aPovValues, aButtonCount, aButtonMask);
    }

    @Override
    public void setMatchInfo(String aEventName, MatchType aMatchType, int aMatchNumber, int aReplayNumber, String aGameSpecificMessage)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public double getTimeSinceEnabled()
    {
        // TODO Auto-generated method stub
        return -1;
    }

}
