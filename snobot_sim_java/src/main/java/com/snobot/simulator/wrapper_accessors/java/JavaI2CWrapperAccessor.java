package com.snobot.simulator.wrapper_accessors.java;

import java.util.Collection;
import java.util.Map;

import com.snobot.simulator.module_wrapper.factories.DefaultI2CSimulatorFactory;
import com.snobot.simulator.module_wrapper.factories.II2cSimulatorFactory;
import com.snobot.simulator.wrapper_accessors.I2CWrapperAccessor;

public class JavaI2CWrapperAccessor implements I2CWrapperAccessor
{
    private II2cSimulatorFactory mI2CFactory = new DefaultI2CSimulatorFactory();

    public void setI2CFactory(II2cSimulatorFactory aFactory)
    {
        mI2CFactory = aFactory;
    }

    @Override
    public Collection<String> getAvailableI2CSimulators()
    {
        return mI2CFactory.getAvailableTypes();
    }

    @Override
    public boolean createI2CSimulator(int aPort, String aType)
    {
        return mI2CFactory.create(aPort, aType);
    }

    @Override
    public Map<Integer, String> getI2CWrapperTypes()
    {
        return mI2CFactory.getI2CWrapperTypes();
    }
}
