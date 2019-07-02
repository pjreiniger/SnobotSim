package com.snobot.simulator.module_wrapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorJniTest;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Solenoid;

public class TestSolenoidJni extends BaseSimulatorJniTest
{
    @Test
    public void testCreateSolenoid()
    {
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSolenoidAccessor().getPortList().size());

        new Solenoid(0);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getSolenoidAccessor().getPortList().size());
        Assertions.assertEquals("Solenoid 0", DataAccessorFactory.getInstance().getSolenoidAccessor().getName(0));
        Assertions.assertFalse(DataAccessorFactory.getInstance().getSolenoidAccessor().getWantsHidden(0));

        new Solenoid(3);
        Assertions.assertEquals(2, DataAccessorFactory.getInstance().getSolenoidAccessor().getPortList().size());
        Assertions.assertEquals("Solenoid 3", DataAccessorFactory.getInstance().getSolenoidAccessor().getName(3));
        Assertions.assertFalse(DataAccessorFactory.getInstance().getSolenoidAccessor().getWantsHidden(3));

        new Solenoid(1, 6);
        Assertions.assertEquals(3, DataAccessorFactory.getInstance().getSolenoidAccessor().getPortList().size());
        Assertions.assertEquals("Solenoid 14", DataAccessorFactory.getInstance().getSolenoidAccessor().getName(14));
        Assertions.assertFalse(DataAccessorFactory.getInstance().getSolenoidAccessor().getWantsHidden(14));

        DataAccessorFactory.getInstance().getSolenoidAccessor().setName(0, "NewNameFor0");
        Assertions.assertEquals("NewNameFor0", DataAccessorFactory.getInstance().getSolenoidAccessor().getName(0));
    }

    @Test
    public void testCreateSolenoidWithSetup()
    {
        DataAccessorFactory.getInstance().getSolenoidAccessor().createSimulator(3, "com.snobot.simulator.module_wrapper.wpi.WpiSolenoidWrapper");
        Assertions.assertFalse(DataAccessorFactory.getInstance().getSolenoidAccessor().isInitialized(3));

        new Solenoid(3);
        Assertions.assertTrue(DataAccessorFactory.getInstance().getSolenoidAccessor().isInitialized(3));
    }

    @Test
    public void testReusePort()
    {
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSolenoidAccessor().getPortList().size());

        new Solenoid(1);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getSolenoidAccessor().getPortList().size());

        Assertions.assertThrows(RuntimeException.class, () ->
        {
            new Solenoid(1);
        });
    }

    @Test
    public void testSet()
    {
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSolenoidAccessor().getPortList().size());

        Solenoid solenoid = new Solenoid(0);

        solenoid.set(true);
        Assertions.assertTrue(solenoid.get());
        Assertions.assertTrue(DataAccessorFactory.getInstance().getSolenoidAccessor().get(0));

        solenoid.set(false);
        Assertions.assertFalse(solenoid.get());
        Assertions.assertFalse(DataAccessorFactory.getInstance().getSolenoidAccessor().get(0));

        DoubleSolenoid doubleSolenoid = new DoubleSolenoid(1, 2);

        doubleSolenoid.set(Value.kForward);
        Assertions.assertTrue(DataAccessorFactory.getInstance().getSolenoidAccessor().get(1));
        Assertions.assertFalse(DataAccessorFactory.getInstance().getSolenoidAccessor().get(2));

        doubleSolenoid.set(Value.kReverse);
        Assertions.assertFalse(DataAccessorFactory.getInstance().getSolenoidAccessor().get(1));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getSolenoidAccessor().get(2));

        doubleSolenoid.set(Value.kOff);
        Assertions.assertFalse(DataAccessorFactory.getInstance().getSolenoidAccessor().get(1));
        Assertions.assertFalse(DataAccessorFactory.getInstance().getSolenoidAccessor().get(2));
    }
}
