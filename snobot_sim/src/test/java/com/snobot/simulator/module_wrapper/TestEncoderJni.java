package com.snobot.simulator.module_wrapper;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.snobot.simulator.motor_sim.SimpleMotorSimulationConfig;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorJniTest;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

public class TestEncoderJni extends BaseSimulatorJniTest
{
    @Test
    public void testCreateEncoder()
    {
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getEncoderAccessor().getPortList().size());

        new Encoder(0, 1);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getEncoderAccessor().getPortList().size());
        Assertions.assertEquals("Encoder 0", DataAccessorFactory.getInstance().getEncoderAccessor().getWrapper(0).getName());
        Assertions.assertFalse(DataAccessorFactory.getInstance().getEncoderAccessor().getWrapper(0).getWantsHidden());

        new Encoder(2, 3);
        Assertions.assertEquals(2, DataAccessorFactory.getInstance().getEncoderAccessor().getPortList().size());
        Assertions.assertEquals("Encoder 1", DataAccessorFactory.getInstance().getEncoderAccessor().getWrapper(1).getName());
        Assertions.assertFalse(DataAccessorFactory.getInstance().getEncoderAccessor().getWrapper(0).getWantsHidden());

        DataAccessorFactory.getInstance().getEncoderAccessor().getWrapper(1).setName("NewNameFor1");
        Assertions.assertEquals("NewNameFor1", DataAccessorFactory.getInstance().getEncoderAccessor().getWrapper(1).getName());
    }

    @Test
    public void testCreateEncoderWithSetup()
    {
        DataAccessorFactory.getInstance().getEncoderAccessor().createSimulator(0, "com.snobot.simulator.module_wrapper.wpi.WpiEncoderWrapper");
        Assertions.assertFalse(DataAccessorFactory.getInstance().getEncoderAccessor().getWrapper(0).isInitialized());

        new Encoder(1, 2);
        Assertions.assertTrue(DataAccessorFactory.getInstance().getEncoderAccessor().getWrapper(0).isInitialized());
    }

    @Test
    public void testReusePort()
    {
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getEncoderAccessor().getPortList().size());

        new Encoder(0, 1);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getEncoderAccessor().getPortList().size());

        Assertions.assertThrows(RuntimeException.class, () ->
        {
            new Encoder(1, 2);
        });
    }

    @Test
    public void testSpeedControllerFeedback()
    {
        SpeedController sc = new Talon(0);
        final Encoder encoder = new Encoder(1, 2);
        Assertions.assertFalse(DataAccessorFactory.getInstance().getEncoderAccessor().isHookedUp(0));
        Assertions.assertEquals(-1, DataAccessorFactory.getInstance().getEncoderAccessor().getHookedUpId(0));

        Assertions.assertFalse(DataAccessorFactory.getInstance().getEncoderAccessor().isHookedUp(0));
        Assertions.assertEquals(-1, DataAccessorFactory.getInstance().getEncoderAccessor().getHookedUpId(0));

        Assertions.assertTrue(DataAccessorFactory.getInstance().getEncoderAccessor().connectSpeedController(0, 0));
        Assertions.assertTrue(
                DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Simple(0, new SimpleMotorSimulationConfig(12)));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getEncoderAccessor().isHookedUp(0));
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getEncoderAccessor().getHookedUpId(0));

        simulateForTime(1, () ->
        {
            sc.set(1);
        });

        Assertions.assertEquals(12.0, encoder.getDistance(), DOUBLE_EPSILON);
        Assertions.assertEquals(12.0, encoder.getRate(), DOUBLE_EPSILON);
        Assertions.assertEquals(12.0, DataAccessorFactory.getInstance().getEncoderAccessor().getDistance(0), DOUBLE_EPSILON);

        encoder.reset();
        Assertions.assertEquals(0.0, encoder.getDistance(), DOUBLE_EPSILON);
        Assertions.assertEquals(0.0, encoder.getRate(), DOUBLE_EPSILON);
    }

    @Disabled("Need to update WPILIB")
    @Test
    public void testSpeedControllerFeedbackWithDistancePerTick()
    {
        SpeedController sc = new Talon(0);
        Assertions.assertFalse(DataAccessorFactory.getInstance().getEncoderAccessor().isHookedUp(0));
        Assertions.assertEquals(-1, DataAccessorFactory.getInstance().getEncoderAccessor().getHookedUpId(0));

        Encoder encoder = new Encoder(1, 2);
        encoder.setDistancePerPulse(.0002);
        Assertions.assertFalse(DataAccessorFactory.getInstance().getEncoderAccessor().isHookedUp(0));
        Assertions.assertEquals(-1, DataAccessorFactory.getInstance().getEncoderAccessor().getHookedUpId(0));

        Assertions.assertTrue(DataAccessorFactory.getInstance().getEncoderAccessor().connectSpeedController(0, 0));
        Assertions.assertTrue(
                DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Simple(0, new SimpleMotorSimulationConfig(12)));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getEncoderAccessor().isHookedUp(0));
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getEncoderAccessor().getHookedUpId(0));

        simulateForTime(1, () ->
        {
            sc.set(1);
        });

        Assertions.assertEquals(12.0, encoder.getDistance(), DOUBLE_EPSILON);
        Assertions.assertEquals(12.0, encoder.getRate(), DOUBLE_EPSILON);
        Assertions.assertEquals(12.0, DataAccessorFactory.getInstance().getEncoderAccessor().getDistance(0), DOUBLE_EPSILON);

        encoder.reset();
        Assertions.assertEquals(0.0, encoder.getDistance(), DOUBLE_EPSILON);
        Assertions.assertEquals(0.0, encoder.getRate(), DOUBLE_EPSILON);
        Assertions.assertEquals(0.0, DataAccessorFactory.getInstance().getEncoderAccessor().getDistance(0), DOUBLE_EPSILON);

        simulateForTime(1, () ->
        {
            sc.set(1);
        });
        Assertions.assertTrue(encoder.getDistance() > 0);

        // Try another reset
        encoder.reset();
        Assertions.assertEquals(0.0, encoder.getDistance(), DOUBLE_EPSILON);
        Assertions.assertEquals(0.0, DataAccessorFactory.getInstance().getEncoderAccessor().getDistance(0), DOUBLE_EPSILON);
    }

    @Test
    public void testInvalidSpeedControllerFeedback()
    {
        Assertions.assertFalse(DataAccessorFactory.getInstance().getEncoderAccessor().connectSpeedController(0, 0));

        new Encoder(1, 2);
        Assertions.assertFalse(DataAccessorFactory.getInstance().getEncoderAccessor().connectSpeedController(0, 0));
    }
}
