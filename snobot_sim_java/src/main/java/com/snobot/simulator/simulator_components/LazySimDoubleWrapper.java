
package com.snobot.simulator.simulator_components;

import edu.wpi.first.hal.SimDouble;
import edu.wpi.first.hal.sim.SimDeviceSim;
import edu.wpi.first.hal.sim.mockdata.SimDeviceDataJNI.SimDeviceInfo;

public class LazySimDoubleWrapper
{
    private final String mDeviceName;
    private final String mValueName;
    private SimDeviceSim mDeviceSim;
    private SimDouble mSimDouble;

    public LazySimDoubleWrapper(String aDeviceName, String aValueName)
    {
        mDeviceName = aDeviceName;
        mValueName = aValueName;
    }

    public double get()
    {
        SimDouble sim = getSimValue();
        if (sim == null)
        {
            System.out.println(
                    "Could not get sim value for device '" + mDeviceName + "' (" + mDeviceSim + "), value '" + mValueName + "' (" + mSimDouble + ")");
            return 0;
        }
        else
        {
            System.out.println("Getting " + sim.get() + " for handle " + sim.getNativeHandle());

            return sim.get();
        }
    }

    public void set(double aValue)
    {
        SimDouble sim = getSimValue();
        if (sim == null)
        {
            System.out.println(
                    "Could not get sim value for device '" + mDeviceName + "' (" + mDeviceSim + "), value '" + mValueName + "' (" + mSimDouble + ")");
        }
        else
        {
            System.out.println("Setting " + aValue + " for handle " + sim.getNativeHandle());
            sim.set(aValue);
        }
    }

    private SimDouble getSimValue()
    {
        if (mSimDouble != null)
        {
            return mSimDouble;
        }

        SimDeviceSim sim = getDeviceSim();
        if (sim != null)
        {
            mSimDouble = sim.getDouble(mValueName);
        }

        return mSimDouble;
    }

    private SimDeviceSim getDeviceSim()
    {
        if (mDeviceSim != null)
        {
            return mDeviceSim;
        }

        SimDeviceInfo[] devices = SimDeviceSim.enumerateDevices(mDeviceName);
        if (devices.length == 1)
        {
            mDeviceSim = new SimDeviceSim(mDeviceName);
        }

        return mDeviceSim;

    }
}
