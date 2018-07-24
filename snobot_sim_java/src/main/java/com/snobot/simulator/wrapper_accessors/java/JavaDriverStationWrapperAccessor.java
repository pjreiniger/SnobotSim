package com.snobot.simulator.wrapper_accessors.java;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.wrapper_accessors.DriverStationDataAccessor;

import edu.wpi.first.hal.sim.mockdata.DriverStationDataJNI;
import edu.wpi.first.hal.sim.mockdata.SimulatorJNI;
import edu.wpi.first.wpilibj.hal.MatchInfoData;

public class JavaDriverStationWrapperAccessor implements DriverStationDataAccessor
{
    private static final Logger sLOGGER = LogManager.getLogger(JavaDriverStationWrapperAccessor.class);

    private double mEnabledTime = -1;

    private double mNextExpectedTime = System.nanoTime() * 1e-9;

    @Override
    public void waitForProgramToStart()
    {
        SimulatorJNI.waitForProgramStart();
    }

    @Override
    public void setDisabled(boolean aDisabled)
    {
        DriverStationDataJNI.setEnabled(!aDisabled);
        DriverStationDataJNI.setDsAttached(!aDisabled);
        mEnabledTime = System.currentTimeMillis() * 1e-3;
    }

    @Override
    public void setAutonomous(boolean aAuton)
    {
        DriverStationDataJNI.setAutonomous(aAuton);
        mEnabledTime = System.currentTimeMillis() * 1e-3;
    }

    @Override
    public double getMatchTime()
    {
        // return DriverStationDataJNI.getMatchTime();
        return 0;
    }

    @Override
    public void waitForNextUpdateLoop(double aUpdatePeriod)
    {
        double currentTime = System.nanoTime() * 1e-9;
        double diff = currentTime - mNextExpectedTime;
        double timeToWait = aUpdatePeriod - diff;

        mNextExpectedTime += aUpdatePeriod;

        try
        {
            if (timeToWait > 0)
            {
                Thread.sleep((long) (timeToWait * 1000));
            }

        }
        catch (Exception e)
        {
            sLOGGER.log(Level.ERROR, e);
        }

        DriverStationDataJNI.notifyNewData();
        // DriverStationDataJNI.setMatchTime(DriverStationDataJNI.getMatchTime()
        // + aUpdatePeriod);
        // DriverStationSimulatorJni.delayForNextUpdateLoop(aUpdatePeriod);
    }

    @Override
    public void setJoystickInformation(int aJoystickHandle, float[] aAxesArray, short[] aPovsArray, int aButtonCount, int aButtonMask)
    {
        DriverStationDataJNI.setJoystickAxes((byte) aJoystickHandle, aAxesArray);
        DriverStationDataJNI.setJoystickPOVs((byte) aJoystickHandle, aPovsArray);
        DriverStationDataJNI.setJoystickButtons((byte) aJoystickHandle, aButtonMask, aButtonCount);
    }

    @Override
    public void setMatchInfo(String aEventName, MatchType aMatchType, int aMatchNumber, int aReplayNumber, String aGameSpecificMessage)
    {
        MatchInfoData data = new MatchInfoData();
        data.eventName = aEventName;
        data.matchType = aMatchType.ordinal();
        data.matchNumber = aMatchNumber;
        data.replayNumber = aReplayNumber;
        data.gameSpecificMessage = aGameSpecificMessage;
        DriverStationDataJNI.setMatchInfo(data);
    }

    @Override
    public double getTimeSinceEnabled()
    {
        double currentTime = System.currentTimeMillis() * 1e-3;
        return currentTime - mEnabledTime;
    }
}
