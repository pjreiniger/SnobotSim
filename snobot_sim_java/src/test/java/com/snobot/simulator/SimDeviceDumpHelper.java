package com.snobot.simulator;

import java.lang.reflect.Field;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.wpi.first.hal.sim.SimDeviceSim;
import edu.wpi.first.hal.sim.mockdata.SimDeviceDataJNI.SimDeviceInfo;

public final class SimDeviceDumpHelper
{
    private static final Logger sLOGGER = LogManager.getLogger(SimDeviceDumpHelper.class);

    private SimDeviceDumpHelper()
    {
        // Nothing to do
    }

    @SuppressWarnings("PMD.ConsecutiveLiteralAppends")
    public static void dumpSimDevices()
    {
        StringBuilder builder = new StringBuilder(200);
        builder.append("***************************************************\nDumping devices:\n");
        for (SimDeviceInfo x : SimDeviceSim.enumerateDevices(""))
        {
            builder.append("Got a device: \n");
            Field privateStringField;
            try 
            {
                privateStringField = SimDeviceInfo.class.getDeclaredField("name");
            
                privateStringField.setAccessible(true);
                
                builder.append("  ").append(privateStringField.get(x)).append('\n');
            } 
            catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) 
            {
                sLOGGER.log(Level.ERROR, "Failed to get sim device", ex);
            }
        }
        builder.append("***************************************************\n");
        sLOGGER.log(Level.INFO, builder);

    }
}
