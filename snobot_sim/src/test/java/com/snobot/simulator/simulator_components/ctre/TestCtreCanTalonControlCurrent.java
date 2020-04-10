package com.snobot.simulator.simulator_components.ctre;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorJniTest;

@Tag("CTRE")
public class TestCtreCanTalonControlCurrent extends BaseSimulatorJniTest
{
    @ParameterizedTest
    @ArgumentsSource(GetCtreTestIds.GetCtreTestIdsFeedbackDevice.class)
    public void testSetWithCurrent(int aCanHandle)
    {
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList().size());
        TalonSRX talon = new TalonSRX(aCanHandle);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList().size());

        talon.set(ControlMode.Current, 5);
    }
}
