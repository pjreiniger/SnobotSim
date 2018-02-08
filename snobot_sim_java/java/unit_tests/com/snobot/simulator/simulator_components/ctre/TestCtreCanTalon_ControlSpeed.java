package com.snobot.simulator.simulator_components.ctre;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.snobot.simulator.motor_sim.DcMotorModelConfig;
import com.snobot.simulator.motor_sim.StaticLoadMotorSimulationConfig;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorTest;

@RunWith(value = Parameterized.class)
public class TestCtreCanTalon_ControlSpeed extends BaseSimulatorTest
{
    @Parameters(name = "{index}: Port={0}, Device={1}")
    public static Collection<Object[]> ddata()
    {
        Collection<Object[]> output = new ArrayList<>();

        for (int i = 0; i < 64; ++i)
        {
            output.add(new Object[]{ i, FeedbackDevice.QuadEncoder });
        }

        return output;
    }

    private final int mCanHandle;
    private final int mRawHandle;
    private final FeedbackDevice mFeedbackDevice;

    public TestCtreCanTalon_ControlSpeed(int aCanHandle, FeedbackDevice aFeedbackDevice)
    {
        mCanHandle = aCanHandle;
        mRawHandle = mCanHandle + 100;
        mFeedbackDevice = aFeedbackDevice;
    }

    @Test
    public void testSetWithSpeedEncoder()
    {
        Assert.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList().size());
        TalonSRX talon = new TalonSRX(mCanHandle);
        Assert.assertEquals(1, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList().size());

        talon.configSelectedFeedbackSensor(mFeedbackDevice, 0, 0);
        checkForFeedbackDevice();

        // Simulate CIM drivetrain
        DcMotorModelConfig motorConfig = DataAccessorFactory.getInstance().getSimulatorDataAccessor().createMotor("CIM", 1, 10, 1);
        Assert.assertTrue(DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Static(mRawHandle, motorConfig,
                new StaticLoadMotorSimulationConfig(.2)));

        talon.config_kP(0, .045, 5);
        talon.config_kF(0, .018, 5);
        talon.config_IntegralZone(0, 1, 5);

        // 55.8 max velocity
        talon.set(ControlMode.Velocity, 40);

        simulateForTime(1, () ->
        {
        });

        Assert.assertEquals(40, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVelocity(mRawHandle), 1);
        Assert.assertEquals(40, talon.getSelectedSensorVelocity(0) / 600.0, 1);
    }

    private void checkForFeedbackDevice()
    {
        switch (mFeedbackDevice)
        {
        case QuadEncoder:
            Assert.assertTrue(DataAccessorFactory.getInstance().getEncoderAccessor().getPortList().contains(mRawHandle));
            break;
        case Analog:
            Assert.assertTrue(DataAccessorFactory.getInstance().getAnalogAccessor().getPortList().contains(mRawHandle));
            break;
        default:
            Assert.assertTrue(false);
        }
    }
}
