package com.snobot.simulator.wrapper_accessors.jni;

import com.snobot.simulator.module_wrapper.interfaces.ISensorWrapper;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseWrapperAccessor<WrapperType extends ISensorWrapper>
{
    private final Map<Integer, WrapperType> mWrapperMap;

    public BaseWrapperAccessor()
    {
        mWrapperMap = new HashMap<>();
    }

    protected boolean register(int aHandle, WrapperType aWrapper)
    {
        mWrapperMap.put(aHandle, aWrapper);
        return true;
    }

    public final void removeSimulator(int aPort)
    {
        try
        {
            getWrapper(aPort).close();
        }
        catch (Exception ex)
        {
            LogManager.getLogger().log(Level.WARN, "Could not close simulator", ex);
        }
        mWrapperMap.remove(aPort);
    }

    public WrapperType getWrapper(int aHandle)
    {
        if (!mWrapperMap.containsKey(aHandle) && getPortList().contains(aHandle))
        {
            register(aHandle, createWrapperForExistingType(aHandle));
        }
        return mWrapperMap.get(aHandle);
    }

    protected abstract WrapperType createWrapperForExistingType(int aHandle);

    protected abstract List<Integer> getPortList();
}
