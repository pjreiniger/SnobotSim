package com.snobot.simulator.simulator_components.rev.unsupported;

import com.revrobotics.CANAnalog;
import com.revrobotics.CANAnalog.AnalogMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.snobot.test.utilities.BaseSimulatorJniTest;
import org.junit.jupiter.api.Test;

public class TestCANAnalogFunctions extends BaseSimulatorJniTest
{
    @Test
    public void testAnnalogFunctions()
    {
        CANSparkMax sc = new CANSparkMax(11, MotorType.kBrushless);

        for (AnalogMode analogType : AnalogMode.values())
        {
            CANAnalog analog = sc.getAnalog(analogType);
            analog.getVoltage();
            analog.getPosition();
            analog.getVelocity();
            analog.setPositionConversionFactor(0);
            analog.setVelocityConversionFactor(0);
            analog.getPositionConversionFactor();
            analog.getVelocityConversionFactor();
            analog.setInverted(false);
            analog.getInverted();
        }
    }
}
