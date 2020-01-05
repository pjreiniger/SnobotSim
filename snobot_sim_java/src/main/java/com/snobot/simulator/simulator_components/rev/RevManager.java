package com.snobot.simulator.simulator_components.rev;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.simulator_components.smart_sc.BaseCanSmartSpeedController;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

public class RevManager
{
    private static final Logger sLOGGER = LogManager.getLogger(RevManager.class);

    public void handleMessage(String aCallback, int aCanPort, ByteBuffer aData)
    {
        aData.order(ByteOrder.LITTLE_ENDIAN);

        switch (aCallback)
        {
        case "Create":
            createSim(aCanPort);
            break;
        case "SetpointCommand":
        {
            // set(aDeviceId, setpoint, auxSetpoint, pidSlot, rsvd);
            float value = aData.getFloat();
            int ctrl = aData.getInt();
            int pidSlot = aData.getInt();
            float arbFeedforward = aData.getFloat();
            int arbFFUnits = aData.getInt();
            System.out.println("Setting " + aCanPort + " to " + value + ", " + ctrl + ", " + pidSlot + ", " + arbFeedforward + ", " + arbFFUnits);
            set(aCanPort, value);
            break;
        }
        case "SetFollow":
        {
            int followerID = aData.getInt();
            int leadId = followerID & 0x3F;

            BaseCanSmartSpeedController leadWrapper = getMotorControllerWrapper(leadId);
            RevSpeedControllerSimWrapper follower = getMotorControllerWrapper(aCanPort);

            sLOGGER.log(Level.INFO, "Setting SparkMax " + aCanPort + " to follow " + leadId);

            leadWrapper.addFollower(follower);

            break;
        }

        ////////////////////////
        // Getters
        ////////////////////////
        case "GetAppliedOutput":
        {
            RevSpeedControllerSimWrapper wrapper = getMotorControllerWrapper(aCanPort);

            float speed = (float) wrapper.get();
            aData.putFloat(0, speed);
            break;

        }
        default:
            sLOGGER.log(Level.WARN, "Unsupported option " + aCallback + "(" + aData.limit() + " bytes)");
            break;
        }
    }

    // @SuppressWarnings("PMD")
    // protected int handleSend(int aDeviceId, int aApiId, ByteBuffer aData)
    // {
    //     aData.order(ByteOrder.LITTLE_ENDIAN);

    //     int output = 0;

    //     String arbAsString = mArbIdLookup.get(aApiId);
    //     if (arbAsString == null)
    //     {
    //         sLOGGER.log(Level.ERROR, "Unknown API Id " + aApiId);
    //         return 1;
    //     }

    //     switch (arbAsString)
    //     {
    //     case "heartbeat":
    //     {
    //         sLOGGER.log(Level.TRACE, "Hearbeat not supported");
    //         break;
    //     }
    //     case "SetpointOut":
    //     {
    //         if (aData.capacity() == 0)
    //         {
    //             return output;
    //         }

    //         float setpoint = aData.getFloat();
    //         short auxSetpoint = aData.getShort();
    //         byte pidSlot = aData.get();
    //         byte rsvd = aData.get();

    //         set(aDeviceId, setpoint, auxSetpoint, pidSlot, rsvd);
    //         break;
    //     }
    //     // Firmware Revision
    //     case "getFirmwareVersion":
    //     {
    //         // Assume this only happens when starting a thing.
    //         createSim(aDeviceId);
    //         break;
    //     }
    //     case "follow":
    //     {
    //         int followerID = aData.getInt();
    //         int leadId = followerID & 0x3F;

    //         BaseCanSmartSpeedController leadWrapper = getMotorControllerWrapper(leadId);
    //         RevSpeedControllerSimWrapper follower = getMotorControllerWrapper(aDeviceId);

    //         sLOGGER.log(Level.TRACE, "Setting SparkMax " + aDeviceId + " to follow " + leadId);

    //         leadWrapper.addFollower(follower);

    //         break;
    //     }
    //     case "SetDriverSet4":
    //     {
    //         int deviceID = aData.getInt();
    //         float value = aData.getFloat();
    //         aData.getInt();
    //         byte pidSlot = aData.get();
    //         aData.getShort();

    //         set(deviceID, value, (short) 0, pidSlot, (byte) 0);
    //         break;
    //     }
    //     default:
    //         sLOGGER.log(Level.DEBUG, "Unsupported option " + arbAsString + "(" + aApiId + ")");
    //         break;
    //     }

    //     return output;
    // }

    // @Override
    // protected int handleRead(int aDeviceId, int aApiId, ByteBuffer aBuffer)
    // {
    //     int output = 0;

    //     String arbAsString = mArbIdLookup.get(aApiId);
    //     if (arbAsString == null)
    //     {
    //         sLOGGER.log(Level.ERROR, "Unknown API Id " + aApiId);
    //         return 1;
    //     }

    //     switch (arbAsString) // NOPMD
    //     {
    //     // Firmware Revision
    //     case "getFirmwareVersion":
    //     {
    //         sLOGGER.log(Level.DEBUG, "Getting firmware version");
    //         writeFirmwareVersion(aBuffer);
    //         break;
    //     }
    //     case "getPeriodicStatus0":
    //     {
    //         writePeriodicStatus0(aDeviceId, aBuffer);
    //         break;
    //     }
    //     default:
    //         for (int i = 0; i < aBuffer.capacity(); ++i)
    //         {
    //             aBuffer.put(i, (byte) 0);
    //         }
    //         sLOGGER.log(Level.DEBUG, "Unsupported option " + arbAsString + "(" + aApiId + ")");
    //         break;
    //     }

    //     return output;
    // }

    protected void createSim(int aCanPort)
    {
        if (!DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList()
                .contains(aCanPort + BaseCanSmartSpeedController.sCAN_SC_OFFSET))
        {
            sLOGGER.log(Level.WARN, "REV Motor Controller is being created dynamically instead of in the config file for port " + aCanPort);

            DataAccessorFactory.getInstance().getSpeedControllerAccessor().createSimulator(aCanPort + BaseCanSmartSpeedController.sCAN_SC_OFFSET,
                    RevSpeedControllerSimWrapper.class.getName());
        }
        SensorActuatorRegistry.get().getSpeedControllers().get(aCanPort + BaseCanSmartSpeedController.sCAN_SC_OFFSET).setInitialized(true);
    }

    protected void set(int aDeviceId, float aSetpoint)
    {
        RevSpeedControllerSimWrapper wrapper = getMotorControllerWrapper(aDeviceId);
        wrapper.set(aSetpoint);
    }

    // protected void writePeriodicStatus0(int aDeviceId, ByteBuffer aBuffer)
    // {
    // RevSpeedControllerSimWrapper wrapper =
    // getMotorControllerWrapper(aDeviceId);
    //
    // aBuffer.order(ByteOrder.LITTLE_ENDIAN);
    // aBuffer.rewind();
    // aBuffer.putShort((short) (wrapper.get() * 32767.0)); // appliedOutput
    // aBuffer.putShort((short) 0); // faults
    // aBuffer.putShort((short) 0); // stickyFaults
    // aBuffer.put((byte) 0); // bits
    // }

    public void reset()
    {

    }

    protected <WrapperType> WrapperType getMotorControllerWrapper(int aCanPort)
    {
        return (WrapperType) SensorActuatorRegistry.get().getSpeedControllers().get(aCanPort + 100);
    }
}
