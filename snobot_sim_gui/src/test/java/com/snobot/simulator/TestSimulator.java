package com.snobot.simulator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.snobot.simulator.gui.SimulatorFrame;
import com.snobot.simulator.wrapper_accessors.SimulatorDataAccessor.SnobotLogLevel;
import com.snobot.test.utilities.BaseSimulatorTest;

@Disabled
public class TestSimulator extends BaseSimulatorTest
{
    private static final long TIME_TO_RUN_MS = 10;

    public class MockSimulator extends Simulator
    {
        boolean mError;

        public MockSimulator(SnobotLogLevel aLogLevel, String aUserConfigDir) throws Exception
        {
            super(aLogLevel, aUserConfigDir, true);
        }

        @Override
        protected void setFrameVisible(SimulatorFrame aFrame)
        {
            // Nothing to do
        }

        @Override
        protected void showInitializationMessage(String aMessage)
        {
            // Nothing to do
        }

        @Override
        protected void exitWithError()
        {
            mError = true;
            stop();
            System.out.println("Exiting with error"); // NOPMD
        }

        protected void runTestForTime(long aMilliseconds) throws Exception
        {
            startSimulation();
            Thread.sleep(aMilliseconds);
            stop();
        }

    }

    @Test
    public void testStartSimulator() throws Exception
    {
        MockSimulator simulator = new MockSimulator(SnobotLogLevel.DEBUG, "test_output/test_start_simulator/");
        simulator.runTestForTime(TIME_TO_RUN_MS);
        Assertions.assertFalse(simulator.mError);
    }

    @Test
    public void testValidUserConfig() throws Exception
    {
        MockSimulator simulator = new MockSimulator(SnobotLogLevel.DEBUG,
                "test_files/SimulatorTest/TestValidUserConfig/");
        simulator.runTestForTime(TIME_TO_RUN_MS);
        Assertions.assertFalse(simulator.mError);
    }

    @Test
    public void testInvalidSimulatorName() throws Exception
    {
        MockSimulator simulator = new MockSimulator(SnobotLogLevel.DEBUG,
                "test_files/SimulatorTest/InvalidSimulatorName/");
        simulator.runTestForTime(TIME_TO_RUN_MS);
        Assertions.assertTrue(simulator.mError);
    }

    @Test
    public void testCustomSimulatorName() throws Exception
    {
        MockSimulator simulator = new MockSimulator(SnobotLogLevel.DEBUG,
                "test_files/SimulatorTest/CustomSimulatorName/");
        simulator.runTestForTime(TIME_TO_RUN_MS);
        Assertions.assertFalse(simulator.mError);
    }
}
