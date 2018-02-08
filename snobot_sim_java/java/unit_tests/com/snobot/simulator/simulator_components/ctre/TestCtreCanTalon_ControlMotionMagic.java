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
public class TestCtreCanTalon_ControlMotionMagic extends BaseSimulatorTest
{
    private final int mCanHandle;
    private final int mRawHandle;
    private final FeedbackDevice mFeedbackDevice;

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

    public TestCtreCanTalon_ControlMotionMagic(int aCanHandle, FeedbackDevice aFeedbackDevice)
    {
        mCanHandle = aCanHandle;
        mRawHandle = mCanHandle + 100;
        mFeedbackDevice = aFeedbackDevice;
    }

    @Test
    public void testSetWithMotionMagic()
    {

        Assert.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList().size());
        TalonSRX talon = new TalonSRX(mCanHandle);
        Assert.assertEquals(1, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList().size());

        // Hookup motor config
        DcMotorModelConfig motorConfig = DataAccessorFactory.getInstance().getSimulatorDataAccessor().createMotor("CIM", 1, 10, 1);
        Assert.assertTrue(DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Static(mRawHandle, motorConfig,
                new StaticLoadMotorSimulationConfig(.2)));

        talon.config_kP(0, .11, 5);
        talon.config_kI(0, .005, 5);
        talon.config_kF(0, 0.018, 5);
        talon.config_IntegralZone(0, 2, 5);

        talon.configSelectedFeedbackSensor(mFeedbackDevice, 0, 5);
        talon.configMotionCruiseVelocity(12 * 600, 0);
        talon.configMotionAcceleration(24 * 600, 0);
        talon.set(ControlMode.MotionMagic, 30 * 12 * 4096);

        simulateForTime(8, () ->
        {
            System.out.println(talon.getClosedLoopError(0));
        });

        Assert.assertEquals(0, talon.getClosedLoopError(0), 2 * 4096);
    }

}
