package com.snobot.simulator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorJavaTest;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;

@SuppressWarnings({"PMD.ExcessiveMethodLength", "PMD.NcssCount", "PMD.DoNotUseThreads"})
public class TestControllerLoop extends BaseSimulatorJavaTest
{
    private static class TestRobot extends IterativeRobot
    {
        public int mDisabledCtr;
        public int mEnabledCtr;
        public int mAutonCtr;

        private final SpeedController mSpeedController0;
        private final SpeedController mSpeedController1;
        private final Relay mRelay;
        private final Solenoid mSolenoid;
        private final Joystick mJoystick;

        public TestRobot()
        {
            mSpeedController0 = new Talon(0);
            mSpeedController1 = new Victor(1);
            mRelay = new Relay(0);
            mSolenoid = new Solenoid(0);
            mJoystick = new Joystick(0);
        }

        @Override
        public void disabledPeriodic()
        {
            ++mDisabledCtr;
        }

        @Override
        public void autonomousPeriodic()
        {
            ++mAutonCtr;
            mRelay.set(Value.kOff);
            mSpeedController0.set(1);
        }

        @Override
        public void teleopPeriodic()
        {
            ++mEnabledCtr;

            mRelay.set(Value.kForward);
            mSpeedController0.set(0);

            mSolenoid.set(mJoystick.getRawButton(1));
            mSpeedController1.set(mJoystick.getRawAxis(0));
        }
    }

    @Disabled("Match Time isn't sim friendly")
    @Test
    public void testControlLoop() throws Exception
    {
        final double DOUBLE_EPSILON = .1;
        TestRobot robot = new TestRobot();

        Thread thread = new Thread(new Runnable()
        {

            @Override
            public void run()
            {
                try
                {
                    robot.startCompetition();
                }
                catch (Exception e) // NOPMD
                {
                    e.printStackTrace(); // NOPMD
                }
            }
        });
        thread.start();

        double waitForDataPeriod = .02;

        DataAccessorFactory.getInstance().getDriverStationAccessor().waitForProgramToStart();

        float[] joystickAxes = new float[]{0.0f};
        short[] joystickPov = new short[] {};
        int joystickButtonCount = 1;

        DataAccessorFactory.getInstance().getDriverStationAccessor().setJoystickInformation(0, joystickAxes, joystickPov, joystickButtonCount, 0);

        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getDriverStationAccessor().getMatchTime(), DOUBLE_EPSILON);

        // Startup in disabled
        simulateForTime(.5, () ->
        {
            DataAccessorFactory.getInstance().getDriverStationAccessor().waitForNextUpdateLoop(waitForDataPeriod);
        });
        Assertions.assertEquals(.5, DataAccessorFactory.getInstance().getDriverStationAccessor().getMatchTime(), DOUBLE_EPSILON);
        Assertions.assertTrue(robot.mDisabledCtr > 0);
        Assertions.assertEquals(Relay.Value.kOff, robot.mRelay.get());
        Assertions.assertEquals(0, robot.mSpeedController0.get(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, robot.mSpeedController1.get(), DOUBLE_EPSILON);
        Assertions.assertFalse(robot.mSolenoid.get());
        Assertions.assertFalse(DataAccessorFactory.getInstance().getRelayAccessor().getWrapper(0).getRelayForwards());
        Assertions.assertFalse(DataAccessorFactory.getInstance().getRelayAccessor().getWrapper(0).getRelayReverse());
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVoltagePercentage(0), DOUBLE_EPSILON);

        // Move to enabled+teleop
        DataAccessorFactory.getInstance().getDriverStationAccessor().setDisabled(false);
        simulateForTime(.5, () ->
        {
            DataAccessorFactory.getInstance().getDriverStationAccessor().waitForNextUpdateLoop(waitForDataPeriod);
        });
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getDriverStationAccessor().getMatchTime(), DOUBLE_EPSILON);
        Assertions.assertTrue(robot.mEnabledCtr > 0);
        Assertions.assertEquals(Relay.Value.kForward, robot.mRelay.get());
        Assertions.assertEquals(0, robot.mSpeedController0.get(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, robot.mSpeedController1.get(), DOUBLE_EPSILON);
        Assertions.assertFalse(robot.mSolenoid.get());
        Assertions.assertTrue(DataAccessorFactory.getInstance().getRelayAccessor().getWrapper(0).getRelayForwards());
        Assertions.assertFalse(DataAccessorFactory.getInstance().getRelayAccessor().getWrapper(0).getRelayReverse());
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVoltagePercentage(0), DOUBLE_EPSILON);

        // Move to enabled+auton
        DataAccessorFactory.getInstance().getDriverStationAccessor().setAutonomous(true);
        simulateForTime(.5, () ->
        {
            DataAccessorFactory.getInstance().getDriverStationAccessor().waitForNextUpdateLoop(waitForDataPeriod);
        });
        Assertions.assertEquals(1.5, DataAccessorFactory.getInstance().getDriverStationAccessor().getMatchTime(), DOUBLE_EPSILON);
        Assertions.assertTrue(robot.mAutonCtr > 0);
        Assertions.assertEquals(Relay.Value.kOff, robot.mRelay.get());
        Assertions.assertEquals(1, robot.mSpeedController0.get(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, robot.mSpeedController1.get(), DOUBLE_EPSILON);
        Assertions.assertFalse(robot.mSolenoid.get());
        Assertions.assertFalse(DataAccessorFactory.getInstance().getRelayAccessor().getWrapper(0).getRelayForwards());
        Assertions.assertFalse(DataAccessorFactory.getInstance().getRelayAccessor().getWrapper(0).getRelayReverse());
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVoltagePercentage(0), DOUBLE_EPSILON);

        // Back to teleop for joystick testing
        DataAccessorFactory.getInstance().getDriverStationAccessor().setAutonomous(false);
        simulateForTime(.5, () ->
        {
            joystickAxes[0] = 1f;
            DataAccessorFactory.getInstance().getDriverStationAccessor().setJoystickInformation(0, joystickAxes, joystickPov, joystickButtonCount, 1);
            DataAccessorFactory.getInstance().getDriverStationAccessor().waitForNextUpdateLoop(waitForDataPeriod);
        });
        Assertions.assertEquals(2, DataAccessorFactory.getInstance().getDriverStationAccessor().getMatchTime(), DOUBLE_EPSILON);
        Assertions.assertEquals(1, robot.mSpeedController1.get(), DOUBLE_EPSILON);
        Assertions.assertTrue(robot.mSolenoid.get());

        simulateForTime(.5, () ->
        {
            joystickAxes[0] = 0f;
            DataAccessorFactory.getInstance().getDriverStationAccessor().setJoystickInformation(0, joystickAxes, joystickPov, joystickButtonCount, 0);
            DataAccessorFactory.getInstance().getDriverStationAccessor().waitForNextUpdateLoop(waitForDataPeriod);
        });
        Assertions.assertEquals(2.5, DataAccessorFactory.getInstance().getDriverStationAccessor().getMatchTime(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, robot.mSpeedController1.get(), DOUBLE_EPSILON);
        Assertions.assertFalse(robot.mSolenoid.get());

        // Back to disabled
        DataAccessorFactory.getInstance().getDriverStationAccessor().setDisabled(true);
        simulateForTime(.5, () ->
        {
            DataAccessorFactory.getInstance().getDriverStationAccessor().waitForNextUpdateLoop(waitForDataPeriod);
        });
        Assertions.assertEquals(3, DataAccessorFactory.getInstance().getDriverStationAccessor().getMatchTime(), DOUBLE_EPSILON);
        Assertions.assertEquals(Relay.Value.kForward, robot.mRelay.get());
        Assertions.assertEquals(0, robot.mSpeedController0.get(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, robot.mSpeedController1.get(), DOUBLE_EPSILON);
        Assertions.assertFalse(robot.mSolenoid.get());
    }

    @AfterEach
    public void cleanup()
    {
        NetworkTableInstance.getDefault().stopServer();
    }

}
