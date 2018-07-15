package com.snobot.simulator.wrapper_accessors.java;

import java.util.Collection;
import java.util.Map;

import com.snobot.simulator.module_wrapper.factories.DefaultSpiSimulatorFactory;
import com.snobot.simulator.module_wrapper.factories.ISpiSimulatorFactory;
import com.snobot.simulator.wrapper_accessors.SpiWrapperAccessor;

public class JavaSpiWrapperAccessor implements SpiWrapperAccessor
{
    private ISpiSimulatorFactory mSpiFactory = new DefaultSpiSimulatorFactory();

    public void setSpiFactory(ISpiSimulatorFactory aFactory)
    {
        mSpiFactory = aFactory;
    }

    @Override
    public Collection<String> getAvailableSpiSimulators()
    {
        return mSpiFactory.getAvailableTypes();
    }

    @Override
    public boolean createSpiSimulator(int aPort, String aType)
    {
        return mSpiFactory.create(aPort, aType);
    }

    @Override
    public Map<Integer, String> getSpiWrapperTypes()
    {
        return mSpiFactory.getSpiWrapperTypes();
    }
}
