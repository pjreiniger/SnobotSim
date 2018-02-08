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
public class TestCtreCanTalon_ControlPosition extends BaseSimulatorTest
{
    @Parameters(name = "{index}: Port={0}, Device={1}")
    public static Collection<Object[]> ddata()
    {
        Collection<Object[]> output = new ArrayList<>();

        for (int i = 0; i < 64; ++i)
        {

            output.add(new Object[]{ i, FeedbackDevice.Analog });
            output.add(new Object[]{ i, FeedbackDevice.QuadEncoder });
        }

        return output;
    }

    private final int mCanHandle;
    private final int mRawHandle;
    private final FeedbackDevice mFeedbackDevice;

    public TestCtreCanTalon_ControlPosition(int aCanHandle, FeedbackDevice aFeedbackDevice)
    {
        mCanHandle = aCanHandle;
        mRawHandle = mCanHandle + 100;
        mFeedbackDevice = aFeedbackDevice;
    }

    @Test
    public void testSetWithPosition()
    {
        Assert.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList().size());
        TalonSRX talon = new TalonSRX(mCanHandle);
        Assert.assertEquals(1, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList().size());

        talon.configSelectedFeedbackSensor(mFeedbackDevice, 0, 5);
        checkForFeedbackDevice();

        // Simulate CIM drivetrain
        DcMotorModelConfig motorConfig = DataAccessorFactory.getInstance().getSimulatorDataAccessor().createMotor("CIM", 1, 10, 1);
        Assert.assertTrue(DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Static(mRawHandle, motorConfig,
                new StaticLoadMotorSimulationConfig(0.01)));

        talon.config_kP(0, .11, 5);
        talon.config_kI(0, .005, 5);
        talon.config_IntegralZone(0, 2, 5);

        talon.set(ControlMode.Position, 36 * 4096);

        simulateForTime(1, () ->
        {
        });

        Assert.assertEquals(36, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPosition(mRawHandle), .5);
        Assert.assertEquals(36, talon.getSelectedSensorPosition(0) / 4096.0, .5);

        talon.setSelectedSensorPosition(0, 0, 0);
        Assert.assertEquals(0, talon.getSelectedSensorPosition(0), .000001);
    }

    private void checkForFeedbackDevice()
    {
        switch (mFeedbackDevice)
        {
        case QuadEncoder:
            Assert.assertTrue(DataAccessorFactory.getInstance().getEncoderAccessor().getPortList().contains(mRawHandle));
            Assert.assertEquals("CAN Encoder (" + mCanHandle + ")", DataAccessorFactory.getInstance().getEncoderAccessor().getName(mRawHandle));
            break;
        case Analog:
            Assert.assertTrue(DataAccessorFactory.getInstance().getAnalogAccessor().getPortList().contains(mRawHandle));
            Assert.assertEquals("CAN Analog (" + mCanHandle + ")", DataAccessorFactory.getInstance().getAnalogAccessor().getName(mRawHandle));
            break;
        default:
            Assert.assertTrue(false);
        }
    }
}
