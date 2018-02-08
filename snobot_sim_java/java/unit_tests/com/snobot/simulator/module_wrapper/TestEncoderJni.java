package com.snobot.simulator.module_wrapper;



import org.junit.Assert;
import org.junit.Test;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.motor_sim.SimpleMotorSimulationConfig;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorTest;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

public class TestEncoderJni extends BaseSimulatorTest
{
    @Test
    public void testCreateEncoder()
    {
        Assert.assertEquals(0, DataAccessorFactory.getInstance().getEncoderAccessor().getPortList().size());

        new Encoder(0, 1);
        Assert.assertEquals(1, DataAccessorFactory.getInstance().getEncoderAccessor().getPortList().size());
        Assert.assertEquals("Encoder 0", DataAccessorFactory.getInstance().getEncoderAccessor().getName(0));
        Assert.assertFalse(DataAccessorFactory.getInstance().getEncoderAccessor().getWantsHidden(0));

        new Encoder(2, 3);
        Assert.assertEquals(2, DataAccessorFactory.getInstance().getEncoderAccessor().getPortList().size());
        Assert.assertEquals("Encoder 1", DataAccessorFactory.getInstance().getEncoderAccessor().getName(1));
        Assert.assertFalse(DataAccessorFactory.getInstance().getEncoderAccessor().getWantsHidden(0));

        DataAccessorFactory.getInstance().getEncoderAccessor().setName(1, "NewNameFor1");
        Assert.assertEquals("NewNameFor1", DataAccessorFactory.getInstance().getEncoderAccessor().getName(1));
    }

    @Test(expected = RuntimeException.class)
    public void testReusePort()
    {
        Assert.assertEquals(0, DataAccessorFactory.getInstance().getEncoderAccessor().getPortList().size());

        new Encoder(0, 1);
        Assert.assertEquals(1, DataAccessorFactory.getInstance().getEncoderAccessor().getPortList().size());

        new Encoder(1, 2);
    }

    @Test
    public void testSpeedControllerFeedback()
    {
        SpeedController sc = new Talon(0);
        Assert.assertFalse(DataAccessorFactory.getInstance().getEncoderAccessor().isHookedUp(0));
        Assert.assertEquals(-1, DataAccessorFactory.getInstance().getEncoderAccessor().getHookedUpId(0));

        Encoder encoder = new Encoder(1, 2);
        Assert.assertFalse(DataAccessorFactory.getInstance().getEncoderAccessor().isHookedUp(0));
        Assert.assertEquals(-1, DataAccessorFactory.getInstance().getEncoderAccessor().getHookedUpId(0));

        Assert.assertTrue(DataAccessorFactory.getInstance().getEncoderAccessor().connectSpeedController(0, 0));
        Assert.assertTrue(
                DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Simple(0, new SimpleMotorSimulationConfig(12)));
        Assert.assertTrue(DataAccessorFactory.getInstance().getEncoderAccessor().isHookedUp(0));
        Assert.assertEquals(0, DataAccessorFactory.getInstance().getEncoderAccessor().getHookedUpId(0));

        simulateForTime(1, () ->
        {
            sc.set(1);
        });

        Assert.assertEquals(12.0, encoder.getDistance(), DOUBLE_EPSILON);
        Assert.assertEquals(12.0, DataAccessorFactory.getInstance().getEncoderAccessor().getDistance(0), DOUBLE_EPSILON);
        Assert.assertEquals(12.0 / 4, DataAccessorFactory.getInstance().getEncoderAccessor().getRaw(0), DOUBLE_EPSILON);

        encoder.reset();
        Assert.assertEquals(0.0, encoder.getDistance(), DOUBLE_EPSILON);
        Assert.assertEquals(0.0, DataAccessorFactory.getInstance().getEncoderAccessor().getDistance(0), DOUBLE_EPSILON);
        Assert.assertEquals(0.0, DataAccessorFactory.getInstance().getEncoderAccessor().getRaw(0), DOUBLE_EPSILON);
    }

    @Test
    public void testSpeedControllerFeedbackWithDistancePerTick()
    {
        SpeedController sc = new Talon(0);
        Assert.assertFalse(DataAccessorFactory.getInstance().getEncoderAccessor().isHookedUp(0));
        Assert.assertEquals(-1, DataAccessorFactory.getInstance().getEncoderAccessor().getHookedUpId(0));

        Encoder encoder = new Encoder(1, 2);
        encoder.setDistancePerPulse(.0002);
        Assert.assertFalse(DataAccessorFactory.getInstance().getEncoderAccessor().isHookedUp(0));
        Assert.assertEquals(-1, DataAccessorFactory.getInstance().getEncoderAccessor().getHookedUpId(0));

        Assert.assertTrue(DataAccessorFactory.getInstance().getEncoderAccessor().connectSpeedController(0, 0));
        Assert.assertTrue(
                DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Simple(0, new SimpleMotorSimulationConfig(12)));
        Assert.assertTrue(DataAccessorFactory.getInstance().getEncoderAccessor().isHookedUp(0));
        Assert.assertEquals(0, DataAccessorFactory.getInstance().getEncoderAccessor().getHookedUpId(0));

        simulateForTime(1, () ->
        {
            sc.set(1);
        });

        Assert.assertEquals(12.0, encoder.getDistance(), DOUBLE_EPSILON);
        Assert.assertEquals(12.0, DataAccessorFactory.getInstance().getEncoderAccessor().getDistance(0), DOUBLE_EPSILON);
        Assert.assertEquals(12.0 / 4, DataAccessorFactory.getInstance().getEncoderAccessor().getRaw(0), DOUBLE_EPSILON);

        encoder.reset();
        Assert.assertEquals(0.0, encoder.getDistance(), DOUBLE_EPSILON);
        Assert.assertEquals(0.0, DataAccessorFactory.getInstance().getEncoderAccessor().getDistance(0), DOUBLE_EPSILON);
        Assert.assertEquals(0.0, DataAccessorFactory.getInstance().getEncoderAccessor().getRaw(0), DOUBLE_EPSILON);

        simulateForTime(1, () ->
        {
            sc.set(1);
        });
        Assert.assertTrue(encoder.getDistance() > 0);

        // Try another reset
        encoder.reset();
        Assert.assertEquals(0.0, encoder.getDistance(), DOUBLE_EPSILON);
        Assert.assertEquals(0.0, DataAccessorFactory.getInstance().getEncoderAccessor().getDistance(0), DOUBLE_EPSILON);
        Assert.assertEquals(0.0, DataAccessorFactory.getInstance().getEncoderAccessor().getRaw(0), DOUBLE_EPSILON);

    }

    @Test
    public void testSimulatorFeedbackNoUpdate()
    {
        Encoder encoder = new Encoder(1, 2);
        EncoderWrapper wrapper = SensorActuatorRegistry.get().getEncoders().get(0);
        wrapper.setPosition(5);
        wrapper.setPosition(5);

        Assert.assertEquals(5.0, encoder.getDistance(), DOUBLE_EPSILON);
        Assert.assertEquals(5.0, DataAccessorFactory.getInstance().getEncoderAccessor().getDistance(0), DOUBLE_EPSILON);
        Assert.assertEquals((int) 5.0 / 4, DataAccessorFactory.getInstance().getEncoderAccessor().getRaw(0), DOUBLE_EPSILON);
    }

    @Test
    public void testInvalidSpeedControllerFeedback()
    {
        Assert.assertFalse(DataAccessorFactory.getInstance().getEncoderAccessor().connectSpeedController(0, 0));

        new Encoder(1, 2);
        Assert.assertFalse(DataAccessorFactory.getInstance().getEncoderAccessor().connectSpeedController(0, 0));
    }
}
