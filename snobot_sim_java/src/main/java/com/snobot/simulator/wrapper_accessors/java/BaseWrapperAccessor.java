package com.snobot.simulator.wrapper_accessors.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.snobot.simulator.module_wrapper.interfaces.ISensorWrapper;

public abstract class BaseWrapperAccessor<Type extends ISensorWrapper>
{
    protected abstract Map<Integer, Type> getMap();

    public Type getWrapper(int aPort)
    {
        return getMap().get(aPort);
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
        return getMap().get(aPort).getWantsHidden();
    }

    public List<Integer> getPortList()
    {
        return new ArrayList<>(getMap().keySet());
    }

    public String getType(int aPort)
    {
        return getMap().get(aPort).getClass().getName();
    }

}
