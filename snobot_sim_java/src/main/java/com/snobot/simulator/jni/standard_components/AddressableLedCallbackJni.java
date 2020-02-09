package com.snobot.simulator.jni.standard_components;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.wpi.WpiAddressableLedWrapper;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import edu.wpi.first.hal.HALValue;
import edu.wpi.first.hal.sim.mockdata.AddressableLEDDataJNI;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddressableLedCallbackJni
{
    private static final Logger sLOGGER = LogManager.getLogger(AddressableLedCallbackJni.class);


    private AddressableLedCallbackJni()
    {

    }

    private static class AddressableLedCallback extends PortBasedNotifyCallback
    {
        public AddressableLedCallback(int aIndex)
        {
            super(aIndex);
        }

        @Override
        public void callback(String aCallbackType, HALValue aHalValue)
        {
            System.out.println("Getting callback:" + aCallbackType);
            if ("Initialized".equals(aCallbackType))
            {
                if (!DataAccessorFactory.getInstance().getAddressableLedAccessor().getPortList().contains(mPort))
                {
                    DataAccessorFactory.getInstance().getAddressableLedAccessor().createSimulator(mPort, WpiAddressableLedWrapper.class.getName());
                    sLOGGER.log(Level.WARN, "Simulator on port " + mPort + " was not registered before starting the robot");
                }
                SensorActuatorRegistry.get().getLeds().get(mPort).setWantsHidden(true);
            }
            else
            {
                sLOGGER.log(Level.ERROR, "Unknown AddressableLed callback " + aCallbackType + " - " + aHalValue);
            }
        }
    }

    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public static void reset()
    {
        for (int i = 0; i < 1; ++i)
        {
            AddressableLEDDataJNI.resetData(i);

            AddressableLEDDataJNI.registerInitializedCallback(i, new AddressableLedCallback(i), false);
        }
    }
}
