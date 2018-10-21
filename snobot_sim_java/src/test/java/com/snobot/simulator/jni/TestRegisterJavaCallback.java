package com.snobot.simulator.jni;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.test.utilities.BaseSimulatorJavaTest;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

public class TestRegisterJavaCallback extends BaseSimulatorJavaTest
{
    private void checkSizes(int aAnalogInSize, int aDigitalSize, int aEncoderSize, int aPcmSize, int aPdpSize, int aNumPwm)
    {
        Assertions.assertEquals(aAnalogInSize, SensorActuatorRegistry.get().getAnalogIn().size());
        Assertions.assertEquals(aDigitalSize, SensorActuatorRegistry.get().getDigitalSources().size());
        Assertions.assertEquals(aEncoderSize, SensorActuatorRegistry.get().getEncoders().size());
        Assertions.assertEquals(aPcmSize, SensorActuatorRegistry.get().getSolenoids().size());
        Assertions.assertEquals(aNumPwm, SensorActuatorRegistry.get().getSpeedControllers().size());
    }

    @Test
    public void testCallback()
    {
        checkSizes(0, 0, 0, 0, 0, 0);

        SpeedController sc0 = new Talon(0);
        SpeedController sc1 = new Talon(1);
        SpeedController sc2 = new Talon(2);
        SpeedController sc3 = new Talon(3);
        checkSizes(0, 0, 0, 0, 0, 4);

        new AnalogInput(0);
        checkSizes(1, 0, 0, 0, 0, 4);

        new DigitalInput(0);
        checkSizes(1, 1, 0, 0, 0, 4);

        new Encoder(1, 2);
        checkSizes(1, 3, 1, 0, 0, 4);

        new Solenoid(0);
        checkSizes(1, 3, 1, 1, 0, 4);
    }
}
