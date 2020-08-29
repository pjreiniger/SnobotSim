package com.snobot.simulator.module_wrapper;

import com.snobot.simulator.module_wrapper.interfaces.ISolenoidWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.snobot.simulator.module_wrapper.wpi.WpiSolenoidWrapper;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorJavaTest;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Solenoid;

public class TestSolenoidJni extends BaseSimulatorJavaTest
{
    @Test
    public void testCreateSolenoid()
    {
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSolenoidAccessor().getPortList().size());

        new Solenoid(0);
        ISolenoidWrapper wrapper0 = DataAccessorFactory.getInstance().getSolenoidAccessor().getWrapper(0);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getSolenoidAccessor().getPortList().size());
        Assertions.assertEquals("Solenoid 0", wrapper0.getName());
        Assertions.assertFalse(wrapper0.getWantsHidden());

        new Solenoid(3);
        ISolenoidWrapper wrapper3 = DataAccessorFactory.getInstance().getSolenoidAccessor().getWrapper(3);
        Assertions.assertEquals(2, DataAccessorFactory.getInstance().getSolenoidAccessor().getPortList().size());
        Assertions.assertEquals("Solenoid 3", wrapper3.getName());
        Assertions.assertFalse(wrapper3.getWantsHidden());

        new Solenoid(1, 6);
        ISolenoidWrapper wrapper14 = DataAccessorFactory.getInstance().getSolenoidAccessor().getWrapper(14);
        Assertions.assertEquals(3, DataAccessorFactory.getInstance().getSolenoidAccessor().getPortList().size());
        Assertions.assertEquals("Solenoid 14", wrapper14.getName());
        Assertions.assertFalse(wrapper14.getWantsHidden());

        wrapper0.setName("NewNameFor0");
        Assertions.assertEquals("NewNameFor0", wrapper0.getName());
    }

    @Test
    public void testCreateSolenoidWithSetup()
    {
        ISolenoidWrapper wrapper = DataAccessorFactory.getInstance().getSolenoidAccessor().createSimulator(3, WpiSolenoidWrapper.class.getName());
        Assertions.assertFalse(wrapper.isInitialized());

        new Solenoid(3);
        Assertions.assertTrue(wrapper.isInitialized());
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

    @Disabled
    @Test
    public void testSet()
    {
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSolenoidAccessor().getPortList().size());

        Solenoid solenoid = new Solenoid(0);
        ISolenoidWrapper wrapper0 = DataAccessorFactory.getInstance().getSolenoidAccessor().getWrapper(0);

        solenoid.set(true);
        Assertions.assertTrue(solenoid.get());
        Assertions.assertTrue(wrapper0.get());

        solenoid.set(false);
        Assertions.assertFalse(solenoid.get());
        Assertions.assertFalse(wrapper0.get());

        DoubleSolenoid doubleSolenoid = new DoubleSolenoid(1, 2);
        ISolenoidWrapper wrapper1 = DataAccessorFactory.getInstance().getSolenoidAccessor().getWrapper(1);
        ISolenoidWrapper wrapper2 = DataAccessorFactory.getInstance().getSolenoidAccessor().getWrapper(2);

        doubleSolenoid.set(Value.kForward);
        Assertions.assertTrue(wrapper1.get());
        Assertions.assertFalse(wrapper2.get());

        doubleSolenoid.set(Value.kReverse);
        Assertions.assertFalse(wrapper1.get());
        Assertions.assertTrue(wrapper2.get());

        doubleSolenoid.set(Value.kOff);
        Assertions.assertFalse(wrapper1.get());
        Assertions.assertFalse(wrapper2.get());
    }
}
