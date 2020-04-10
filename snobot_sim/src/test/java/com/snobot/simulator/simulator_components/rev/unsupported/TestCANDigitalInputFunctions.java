package com.snobot.simulator.simulator_components.rev.unsupported;

import com.snobot.test.utilities.BaseSimulatorJniTest;

import com.revrobotics.CANDigitalInput;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import org.junit.jupiter.api.Test;

public class TestCANDigitalInputFunctions extends BaseSimulatorJniTest
{
    @Test
    public void testLimitSwitchFunctions()
    {
        CANSparkMax sc = new CANSparkMax(11, MotorType.kBrushless);

        for (LimitSwitchPolarity polarity : LimitSwitchPolarity.values())
        {
            CANDigitalInput reverseSwitch = sc.getReverseLimitSwitch(polarity);
            CANDigitalInput fowrardSwitch = sc.getForwardLimitSwitch(polarity);

            reverseSwitch.enableLimitSwitch(false);
            reverseSwitch.enableLimitSwitch(true);
            reverseSwitch.get();
            reverseSwitch.isLimitSwitchEnabled();

            fowrardSwitch.enableLimitSwitch(false);
            fowrardSwitch.enableLimitSwitch(true);
            fowrardSwitch.get();
            fowrardSwitch.isLimitSwitchEnabled();
        }

    }
}
