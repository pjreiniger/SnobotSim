package com.snobot.simulator.wrapper_accessors.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.snobot.simulator.module_wrapper.factories.BaseWrapperFactory;
import com.snobot.simulator.module_wrapper.interfaces.ISensorWrapper;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

public abstract class BaseWrapperAccessor<Type extends ISensorWrapper>
{
    protected final BaseWrapperFactory mFactory;

    public BaseWrapperAccessor(BaseWrapperFactory aFactory)
    {
        mFactory = aFactory;
    }

    public final Type createSimulator(int aPort, String aType)
    {
        if (mFactory.create(aPort, aType))
        {
            return getWrapper(aPort);
        }

        return null;
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
        getWrappers().remove(aPort);
    }

    public Type getWrapper(int aPort)
    {
        return getWrappers().get(aPort);
    }

    public void setName(int aPort, String aName)
    {
        Type object = getWrapper(aPort);
        if (object != null)
        {
            object.setName(aName);
        }
    }

    public String getName(int aPort)
    {
        return getWrapper(aPort).getName();
    }

    public boolean getWantsHidden(int aPort)
    {
        return getWrappers().get(aPort).getWantsHidden();
    }

    public List<Integer> getPortList()
    {
        return new ArrayList<>(getWrappers().keySet());
    }

    public String getType(int aPort)
    {
        return getWrappers().get(aPort).getClass().getName();
    }

    protected abstract Map<Integer, Type> getWrappers();
}
