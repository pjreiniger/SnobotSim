package com.snobot.simulator.module_wrapper.wpi;

import com.snobot.simulator.module_wrapper.ASensorWrapper;
import com.snobot.simulator.module_wrapper.interfaces.IAddressableLedWrapper;
import edu.wpi.first.hal.sim.AddressableLEDSim;

public class WpiAddressableLedWrapper extends ASensorWrapper implements IAddressableLedWrapper
{
    private final AddressableLEDSim mWpiSimulator;

    public WpiAddressableLedWrapper(int aPort)
    {
        super("Addressable LED " + aPort);

        mWpiSimulator = new AddressableLEDSim(aPort);
    }


    @Override
    public byte[] getData()
    {
        return mWpiSimulator.getData();
    }
}
