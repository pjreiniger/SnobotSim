package com.snobot.simulator.wrapper_accessors.jni;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseWrapperAccessor<WrapperType>
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

    protected WrapperType getWrapper(int aHandle)
    {
        if (!mWrapperMap.containsKey(aHandle) && getPortList().contains(aHandle))
        {
            register(aHandle, createWrapperForExistingType(aHandle));
        }
        return mWrapperMap.get(aHandle);
    }

    protected abstract WrapperType createWrapperForExistingType(int aHandle);

    protected abstract List<Integer> getPortList();

    void removeWrapper(int aHandle)
    {
        mWrapperMap.remove(aHandle);
    }

}
