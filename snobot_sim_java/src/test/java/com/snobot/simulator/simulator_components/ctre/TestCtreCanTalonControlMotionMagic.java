package com.snobot.simulator.simulator_components.ctre;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.snobot.simulator.motor_sim.DcMotorModelConfig;
import com.snobot.simulator.motor_sim.StaticLoadMotorSimulationConfig;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorJavaTest;

@Tag("CTRE")
public class TestCtreCanTalonControlMotionMagic extends BaseSimulatorJavaTest
{
    public static Collection<Object[]> getData()
    {
        Collection<Object[]> output = new ArrayList<>();

        for (int i = 0; i < 64; ++i)
        {

            output.add(new Object[]{ i, FeedbackDevice.Analog });
            output.add(new Object[]{ i, FeedbackDevice.QuadEncoder });
        }

        return output;
    }

    @ParameterizedTest
    @MethodSource("getData")
    public void testSetWithMotionMagic(int aCanHandle, FeedbackDevice aFeedbackDevice)
    {
        int rawHandle = aCanHandle + 100;

        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList().size());
        TalonSRX talon = new TalonSRX(aCanHandle);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList().size());

        // Hookup motor config
        DcMotorModelConfig motorConfig = DataAccessorFactory.getInstance().getSimulatorDataAccessor().createMotor("CIM", 1, 10, 1);
        Assertions.assertTrue(DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Static(rawHandle, motorConfig,
                new StaticLoadMotorSimulationConfig(.2)));

        talon.config_kP(0, .11, 5);
        talon.config_kI(0, .005, 5);
        talon.config_kF(0, 0.018, 5);
        talon.config_IntegralZone(0, 2, 5);

        talon.configSelectedFeedbackSensor(aFeedbackDevice, 0, 5);
        talon.configMotionCruiseVelocity(12 * 600, 0);
        talon.configMotionAcceleration(24 * 600, 0);
        talon.set(ControlMode.MotionMagic, 30 * 12 * 4096);

        simulateForTime(8, () ->
        {
            System.out.println(talon.getClosedLoopError(0)); // NOPMD
        });

        Assertions.assertEquals(0, talon.getClosedLoopError(0), 2 * 4096);
    }

}
