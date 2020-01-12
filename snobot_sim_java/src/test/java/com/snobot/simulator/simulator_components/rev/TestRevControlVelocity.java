package com.snobot.simulator.simulator_components.rev;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;
import com.snobot.simulator.motor_sim.DcMotorModelConfig;
import com.snobot.simulator.motor_sim.StaticLoadMotorSimulationConfig;
import com.snobot.simulator.simulator_components.ctre.CtreTalonSrxSpeedControllerSim;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorJavaTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Collection;

public class TestRevControlVelocity extends BaseSimulatorJavaTest
{
    public static Collection<Integer> getData()
    {
        Collection<Integer> output = new ArrayList<>();

        output.add(7);

        return output;
    }

    @ParameterizedTest
    @MethodSource("getData")
    public void testVelocityControl(int aCanHandle)
    {
        int rawHandle = aCanHandle + CtreTalonSrxSpeedControllerSim.sCAN_SC_OFFSET;

        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList().size());

        CANSparkMax sparksMax = new CANSparkMax(aCanHandle, CANSparkMaxLowLevel.MotorType.kBrushless);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList().size());
        Assertions.assertEquals("Rev SC " + aCanHandle,
                DataAccessorFactory.getInstance().getSpeedControllerAccessor().getName(rawHandle));

        // Simulate CIM drivetrain
        DcMotorModelConfig motorConfig = DataAccessorFactory.getInstance().getSimulatorDataAccessor().createMotor("CIM", 1, 10, 1);
        Assertions.assertTrue(DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Static(rawHandle, motorConfig,
                new StaticLoadMotorSimulationConfig(.2)));

        CANPIDController pid = sparksMax.getPIDController();
        CANEncoder encoder = sparksMax.getEncoder();
        pid.setFeedbackDevice(encoder);

//        kDutyCycle(0), kVelocity(1), kVoltage(2), kPosition(3), kSmartMotion(4), kCurrent(5), kSmartVelocity(6);

        pid.setP(.04);
        pid.setFF(.019);

        simulateForTime(1, () ->
        {
            System.out.println(encoder.getVelocity());
            pid.setReference(40, ControlType.kVelocity);
        });

        Assertions.assertEquals(40, encoder.getVelocity(), 1);
        Assertions.assertEquals(40, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVelocity(rawHandle), 1);
    }
}
