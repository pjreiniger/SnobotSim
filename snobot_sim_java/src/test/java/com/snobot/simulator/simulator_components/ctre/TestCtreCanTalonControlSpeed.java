package com.snobot.simulator.simulator_components.ctre;

import com.snobot.simulator.module_wrapper.interfaces.IPwmWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.snobot.simulator.motor_sim.DcMotorModelConfig;
import com.snobot.simulator.motor_sim.StaticLoadMotorSimulationConfig;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorJavaTest;

@Tag("CTRE")
public class TestCtreCanTalonControlSpeed extends BaseSimulatorJavaTest
{
    @ParameterizedTest
    @ArgumentsSource(GetCtreTestIds.GetCtreTestIdsWithFeedbackDevice.class)
    public void testSetWithSpeedEncoder(int aCanHandle, FeedbackDevice aFeedbackDevice)
    {
        final int rawHandle = aCanHandle + 100;

        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getWrappers().size());
        TalonSRX talon = new TalonSRX(aCanHandle);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getWrappers().size());

        IPwmWrapper wrapper = DataAccessorFactory.getInstance().getSpeedControllerAccessor().getWrapper(rawHandle);

        talon.configSelectedFeedbackSensor(aFeedbackDevice, 0, 0);
        checkForFeedbackDevice(rawHandle, aFeedbackDevice);

        // Simulate CIM drivetrain
        DcMotorModelConfig motorConfig = DataAccessorFactory.getInstance().getSimulatorDataAccessor().createMotor("CIM", 1, 10, 1);
        Assertions.assertTrue(DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Static(rawHandle, motorConfig,
                new StaticLoadMotorSimulationConfig(.2)));

        talon.config_kP(0, .045, 5);
        talon.config_kF(0, .018, 5);
        talon.config_IntegralZone(0, 1, 5);

        // 55.8 max velocity
        talon.set(ControlMode.Velocity, 40);

        simulateForTime(1, () ->
        {
        });

        Assertions.assertEquals(40, wrapper.getVelocity(), 1);
        Assertions.assertEquals(40, talon.getSelectedSensorVelocity(0) / 600.0, 1);
    }

    private void checkForFeedbackDevice(int aRawHandle, FeedbackDevice aFeedbackDevice)
    {
        switch (aFeedbackDevice)
        {
        case QuadEncoder:
            Assertions.assertTrue(DataAccessorFactory.getInstance().getEncoderAccessor().getWrappers().containsKey(aRawHandle));
            break;
        case Analog:
            Assertions.assertTrue(DataAccessorFactory.getInstance().getAnalogInAccessor().getWrappers().containsKey(aRawHandle));
            break;
        default:
            Assertions.assertTrue(false);
            break;
        }
    }
}
