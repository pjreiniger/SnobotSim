package com.snobot.simulator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.simulator.wrapper_accessors.DriverStationDataAccessor;
import com.snobot.test.utilities.BaseSimulatorJavaTest;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.MatchType;

@Disabled
public class TestMatchData extends BaseSimulatorJavaTest
{

    @Test
    public void testMatchData()
    {
        Assertions.assertEquals("", DriverStation.getInstance().getEventName());
        Assertions.assertEquals(MatchType.None, DriverStation.getInstance().getMatchType());
        Assertions.assertEquals(0, DriverStation.getInstance().getMatchNumber());
        Assertions.assertEquals(0, DriverStation.getInstance().getReplayNumber());
        Assertions.assertEquals("", DriverStation.getInstance().getGameSpecificMessage());
        
        DataAccessorFactory.getInstance().getDriverStationAccessor().setMatchInfo(
                "Event Name",
                DriverStationDataAccessor.MatchType.Qualification,
                55,
                2,
                "Game Data");

        DataAccessorFactory.getInstance().getDriverStationAccessor().waitForNextUpdateLoop();

        // Run for a little bit to get the driver station to take the data
        try
        {
            Thread.sleep(500);
        }
        catch (InterruptedException e)
        {
            // Ignore
        }
        DataAccessorFactory.getInstance().getDriverStationAccessor().waitForNextUpdateLoop();

        Assertions.assertEquals("Event Name", DriverStation.getInstance().getEventName());
        Assertions.assertEquals(MatchType.Qualification, DriverStation.getInstance().getMatchType());
        Assertions.assertEquals(55, DriverStation.getInstance().getMatchNumber());
        Assertions.assertEquals(2, DriverStation.getInstance().getReplayNumber());
        Assertions.assertEquals("Game Data", DriverStation.getInstance().getGameSpecificMessage());
    }
}
