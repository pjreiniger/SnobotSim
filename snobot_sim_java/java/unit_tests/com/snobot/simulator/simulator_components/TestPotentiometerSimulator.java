package com.snobot.simulator.simulator_components;

import org.junit.Assert;
import org.junit.Test;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.AnalogWrapper;
import com.snobot.simulator.module_wrapper.PwmWrapper;
import com.snobot.simulator.motor_sim.SimpleMotorSimulationConfig;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorTest;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

public class TestPotentiometerSimulator extends BaseSimulatorTest
{
    @Test
    public void testPotentiometer()
    {
        double range = 83;
        double offset = 0;

        final Potentiometer potentiometer = new AnalogPotentiometer(0, range, offset);
        SpeedController sc = new Talon(0);

        AnalogWrapper analogWrapper = SensorActuatorRegistry.get().getAnalog().get(0);
        PwmWrapper pwmWrapper = SensorActuatorRegistry.get().getSpeedControllers().get(0);
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Simple(0, new SimpleMotorSimulationConfig(1));

        PotentiometerSimulator potSim = new PotentiometerSimulator(analogWrapper, pwmWrapper);
        Assert.assertTrue(potSim.isSetup());
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

        Assert.assertEquals(73, potentiometer.get(), DOUBLE_EPSILON);
        Assert.assertEquals(73, pwmWrapper.getPosition(), DOUBLE_EPSILON);
        Assert.assertEquals(4.39759, DataAccessorFactory.getInstance().getAnalogAccessor().getVoltage(0), DOUBLE_EPSILON);
    }

    @Test
    public void testIncompletePotSim()
    {
        PotentiometerSimulator pot = new PotentiometerSimulator(null, null);
        Assert.assertFalse(pot.isSetup());

        new AnalogInput(0);
        pot = new PotentiometerSimulator(SensorActuatorRegistry.get().getAnalog().get(0), null);
        Assert.assertFalse(pot.isSetup());
    }
}
