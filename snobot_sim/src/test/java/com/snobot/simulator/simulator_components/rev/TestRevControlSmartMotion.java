package com.snobot.simulator.simulator_components.rev;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;
import com.snobot.simulator.module_wrapper.interfaces.IPwmWrapper;
import com.snobot.simulator.motor_sim.DcMotorModelConfig;
import com.snobot.simulator.motor_sim.StaticLoadMotorSimulationConfig;
import com.snobot.simulator.wrapper_accessors.SpeedControllerWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.jni.JniSpeedControllerWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorJniTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

public class TestRevControlSmartMotion extends BaseSimulatorJniTest
{
    @ParameterizedTest
    @ArgumentsSource(GetRevTestIds.class)
    public void testPositionControl(int aCanHandle)
    {
        SpeedControllerWrapperAccessor scAccessor = DataAccessorFactory.getInstance().getSpeedControllerAccessor();

        int rawHandle = aCanHandle + JniSpeedControllerWrapperAccessor.sCAN_SC_OFFSET;

        Assertions.assertEquals(0, scAccessor.getWrappers().size());

        CANSparkMax sparksMax = new CANSparkMax(aCanHandle, CANSparkMaxLowLevel.MotorType.kBrushless);
        IPwmWrapper wrapper = DataAccessorFactory.getInstance().getSpeedControllerAccessor().getWrapper(rawHandle);
        Assertions.assertEquals(1, scAccessor.getWrappers().size());
        Assertions.assertEquals("Rev SC " + aCanHandle, wrapper.getName());

        // Simulate CIM drivetrain
        DcMotorModelConfig motorConfig = DataAccessorFactory.getInstance().getSimulatorDataAccessor().createMotor("CIM", 1, 10, 1);
        Assertions.assertTrue(DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Static(rawHandle, motorConfig,
                new StaticLoadMotorSimulationConfig(0.01)));

        CANPIDController pid = sparksMax.getPIDController();
        CANEncoder encoder = sparksMax.getEncoder();
        pid.setFeedbackDevice(encoder);

//        kDutyCycle(0), kVelocity(1), kVoltage(2), kPosition(3), kSmartMotion(4), kCurrent(5), kSmartVelocity(6);

        pid.setP(.11);
        pid.setI(.005);
        pid.setFF(0.08);
        pid.setSmartMotionMaxVelocity(12, 0);
        pid.setSmartMotionMaxAccel(24, 0);

        simulateForTime(12, () ->
        {
            pid.setReference(36, ControlType.kSmartMotion);
        });

        Assertions.assertEquals(36, encoder.getPosition(), .5);
        Assertions.assertEquals(36, wrapper.getPosition(), .5);
    }
}
