package com.snobot.simulator;

import org.junit.After;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorTest;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;

public class TestControllerLoop extends BaseSimulatorTest
{
    private static class TestRobot extends IterativeRobot
    {
        public int mDisabledCtr = 0;
        public int mEnabledCtr = 0;
        public int mAutonCtr = 0;

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

        public void disabledPeriodic()
        {
            ++mDisabledCtr;
        }

        public void autonomousPeriodic()
        {
            ++mAutonCtr;
            mRelay.set(Value.kOff);
            mSpeedController0.set(1);
        }

        public void teleopPeriodic()
        {
            ++mEnabledCtr;

            mRelay.set(Value.kForward);
            mSpeedController0.set(0);

            mSolenoid.set(mJoystick.getRawButton(1));
            mSpeedController1.set(mJoystick.getRawAxis(0));
        }
    }

    @Ignore("Match Time isn't sim friendly")
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
                catch (Exception e)
                {
                    e.printStackTrace(); // NOPMD
                }
            }
        });
        thread.start();

        double waitForDataPeriod = .02;

        DataAccessorFactory.getInstance().getSimulatorDataAccessor().waitForProgramToStart();

        float[] joystickAxes = new float[]{0.0f};
        short[] joystickPov = new short[] {};
        int joystickButtonCount = 1;

        DataAccessorFactory.getInstance().getSimulatorDataAccessor().setJoystickInformation(0, joystickAxes, joystickPov, joystickButtonCount, 0);

        Assert.assertEquals(0, DataAccessorFactory.getInstance().getSimulatorDataAccessor().getMatchTime(), DOUBLE_EPSILON);

        // Startup in disabled
        simulateForTime(.5, () ->
        {
            DataAccessorFactory.getInstance().getSimulatorDataAccessor().waitForNextUpdateLoop(waitForDataPeriod);
        });
        Assert.assertEquals(.5, DataAccessorFactory.getInstance().getSimulatorDataAccessor().getMatchTime(), DOUBLE_EPSILON);
        Assert.assertTrue(robot.mDisabledCtr > 0);
        Assert.assertEquals(Relay.Value.kOff, robot.mRelay.get());
        Assert.assertEquals(0, robot.mSpeedController0.get(), DOUBLE_EPSILON);
        Assert.assertEquals(0, robot.mSpeedController1.get(), DOUBLE_EPSILON);
        Assert.assertFalse(robot.mSolenoid.get());
        Assert.assertFalse(DataAccessorFactory.getInstance().getRelayAccessor().getFowardValue(0));
        Assert.assertFalse(DataAccessorFactory.getInstance().getRelayAccessor().getReverseValue(0));
        Assert.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVoltagePercentage(0), DOUBLE_EPSILON);

        // Move to enabled+teleop
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().setDisabled(false);
        simulateForTime(.5, () ->
        {
            DataAccessorFactory.getInstance().getSimulatorDataAccessor().waitForNextUpdateLoop(waitForDataPeriod);
        });
        Assert.assertEquals(1, DataAccessorFactory.getInstance().getSimulatorDataAccessor().getMatchTime(), DOUBLE_EPSILON);
        Assert.assertTrue(robot.mEnabledCtr > 0);
        Assert.assertEquals(Relay.Value.kForward, robot.mRelay.get());
        Assert.assertEquals(0, robot.mSpeedController0.get(), DOUBLE_EPSILON);
        Assert.assertEquals(0, robot.mSpeedController1.get(), DOUBLE_EPSILON);
        Assert.assertFalse(robot.mSolenoid.get());
        Assert.assertTrue(DataAccessorFactory.getInstance().getRelayAccessor().getFowardValue(0));
        Assert.assertFalse(DataAccessorFactory.getInstance().getRelayAccessor().getReverseValue(0));
        Assert.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVoltagePercentage(0), DOUBLE_EPSILON);

        // Move to enabled+auton
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().setAutonomous(true);
        simulateForTime(.5, () ->
        {
            DataAccessorFactory.getInstance().getSimulatorDataAccessor().waitForNextUpdateLoop(waitForDataPeriod);
        });
        Assert.assertEquals(1.5, DataAccessorFactory.getInstance().getSimulatorDataAccessor().getMatchTime(), DOUBLE_EPSILON);
        Assert.assertTrue(robot.mAutonCtr > 0);
        Assert.assertEquals(Relay.Value.kOff, robot.mRelay.get());
        Assert.assertEquals(1, robot.mSpeedController0.get(), DOUBLE_EPSILON);
        Assert.assertEquals(0, robot.mSpeedController1.get(), DOUBLE_EPSILON);
        Assert.assertFalse(robot.mSolenoid.get());
        Assert.assertFalse(DataAccessorFactory.getInstance().getRelayAccessor().getFowardValue(0));
        Assert.assertFalse(DataAccessorFactory.getInstance().getRelayAccessor().getReverseValue(0));
        Assert.assertEquals(1, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVoltagePercentage(0), DOUBLE_EPSILON);

        // Back to teleop for joystick testing
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().setAutonomous(false);
        simulateForTime(.5, () ->
        {
            joystickAxes[0] = 1f;
            DataAccessorFactory.getInstance().getSimulatorDataAccessor().setJoystickInformation(0, joystickAxes, joystickPov, joystickButtonCount, 1);
            DataAccessorFactory.getInstance().getSimulatorDataAccessor().waitForNextUpdateLoop(waitForDataPeriod);
        });
        Assert.assertEquals(2, DataAccessorFactory.getInstance().getSimulatorDataAccessor().getMatchTime(), DOUBLE_EPSILON);
        Assert.assertEquals(1, robot.mSpeedController1.get(), DOUBLE_EPSILON);
        Assert.assertTrue(robot.mSolenoid.get());

        simulateForTime(.5, () ->
        {
            joystickAxes[0] = 0f;
            DataAccessorFactory.getInstance().getSimulatorDataAccessor().setJoystickInformation(0, joystickAxes, joystickPov, joystickButtonCount, 0);
            DataAccessorFactory.getInstance().getSimulatorDataAccessor().waitForNextUpdateLoop(waitForDataPeriod);
        });
        Assert.assertEquals(2.5, DataAccessorFactory.getInstance().getSimulatorDataAccessor().getMatchTime(), DOUBLE_EPSILON);
        Assert.assertEquals(0, robot.mSpeedController1.get(), DOUBLE_EPSILON);
        Assert.assertFalse(robot.mSolenoid.get());

        // Back to disabled
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().setDisabled(true);
        simulateForTime(.5, () ->
        {
            DataAccessorFactory.getInstance().getSimulatorDataAccessor().waitForNextUpdateLoop(waitForDataPeriod);
        });
        Assert.assertEquals(3, DataAccessorFactory.getInstance().getSimulatorDataAccessor().getMatchTime(), DOUBLE_EPSILON);
        Assert.assertEquals(Relay.Value.kForward, robot.mRelay.get());
        Assert.assertEquals(0, robot.mSpeedController0.get(), DOUBLE_EPSILON);
        Assert.assertEquals(0, robot.mSpeedController1.get(), DOUBLE_EPSILON);
        Assert.assertFalse(robot.mSolenoid.get());
    }

    @After
    public void cleanup()
    {
        NetworkTableInstance.getDefault().stopServer();
    }

}
