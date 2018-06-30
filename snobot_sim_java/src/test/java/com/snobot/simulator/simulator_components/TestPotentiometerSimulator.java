package com.snobot.simulator.simulator_components;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.interfaces.IAnalogInWrapper;
import com.snobot.simulator.module_wrapper.interfaces.IPwmWrapper;
import com.snobot.simulator.motor_sim.SimpleMotorSimulationConfig;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorJavaTest;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

public class TestPotentiometerSimulator extends BaseSimulatorJavaTest
{
    @Test
    public void testPotentiometer()
    {
        double range = 83;
        double offset = 0;

        final Potentiometer potentiometer = new AnalogPotentiometer(0, range, offset);
        SpeedController sc = new Talon(0);

        IAnalogInWrapper analogWrapper = SensorActuatorRegistry.get().getAnalogIn().get(0);
        IPwmWrapper pwmWrapper = SensorActuatorRegistry.get().getSpeedControllers().get(0);
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Simple(0, new SimpleMotorSimulationConfig(1));

        PotentiometerSimulator potSim = new PotentiometerSimulator(analogWrapper, pwmWrapper);
        Assertions.assertTrue(potSim.isSetup());
        potSim.setParameters(range, 0, 5);

        // Bring Up
        simulateForTime(83, () ->
        {
            sc.set(1);
        });

        // Back down
        simulateForTime(10, () ->
        {
            sc.set(-1);
        });

        Assertions.assertEquals(73, potentiometer.get(), DOUBLE_EPSILON);
        Assertions.assertEquals(73, pwmWrapper.getPosition(), DOUBLE_EPSILON);
        Assertions.assertEquals(4.39759, DataAccessorFactory.getInstance().getAnalogInAccessor().getVoltage(0), DOUBLE_EPSILON);
    }

    @Test
    public void testIncompletePotSim()
    {
        PotentiometerSimulator pot = new PotentiometerSimulator(null, null);
        Assertions.assertFalse(pot.isSetup());

        new AnalogInput(0);
        pot = new PotentiometerSimulator(SensorActuatorRegistry.get().getAnalogIn().get(0), null);
        Assertions.assertFalse(pot.isSetup());
    }
}
