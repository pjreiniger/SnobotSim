package com.snobot.simulator.wrapper_accessors.java;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.factories.DefaultAddressableWrapperFactory;
import com.snobot.simulator.module_wrapper.interfaces.IAddressableLedWrapper;
import com.snobot.simulator.wrapper_accessors.AddressableLedWrapperAccessor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import java.util.Map;

public class JavaAddressableWrapperAccessor extends BaseWrapperAccessor<IAddressableLedWrapper> implements AddressableLedWrapperAccessor
{

    private final DefaultAddressableWrapperFactory mFactory;

    public JavaAddressableWrapperAccessor()
    {
        mFactory = new DefaultAddressableWrapperFactory();
    }

    @Override
    public boolean createSimulator(int aPort, String aType)
    {
        return mFactory.create(aPort, aType);
    }

    @Override
    public void removeSimulator(int aPort)
    {
        try
        {
            getWrapper(aPort).close();
        }
        catch (Exception ex)
        {
            LogManager.getLogger().log(Level.WARN, "Could not close simulator", ex);
        }
        SensorActuatorRegistry.get().getLeds().remove(aPort);
    }

    @Override
    protected Map<Integer, IAddressableLedWrapper> getMap()
    {
        return SensorActuatorRegistry.get().getLeds();
    }
}
