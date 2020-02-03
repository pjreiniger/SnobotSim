package com.snobot.simulator.simulator_components.rev;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.simulator_components.smart_sc.BaseCanSmartSpeedController;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

@SuppressWarnings({"PMD.ExcessiveMethodLength", "PMD.NcssCount", "PMD.CyclomaticComplexity"})
public class RevManager
{
    private static final Logger sLOGGER = LogManager.getLogger(RevManager.class);

    public void handleMessage(String aCallback, int aCanPort, ByteBuffer aData)
    {
        aData.order(ByteOrder.LITTLE_ENDIAN);

        Set<String> unsupportedFunctions = new HashSet<>();
        unsupportedFunctions.add("GetFault");

        switch (aCallback)
        {
        case "Create":
            createSim(aCanPort);
            break;
        case "SetpointCommand":
        {
            RevSpeedControllerSimWrapper wrapper = getMotorControllerWrapper(aCanPort);

            float value = aData.getFloat();
            int ctrl = aData.getInt();
            /*
            int pidSlot = aData.getInt();
            float arbFeedforward = aData.getFloat();
            int arbFFUnits = aData.getInt();
            */
            switch (ctrl)
            {
            // Throttle
            case 0:
                wrapper.set(value);
                break;
            // Velocity
            case 1:
                wrapper.setSpeedGoal(value);
                break;
            // Position
            case 3:
                wrapper.setPositionGoal(value);
                break;
            // SmartMotion
            case 4:
                wrapper.setMotionMagicGoal(value);
                break;
            default:
                sLOGGER.log(Level.ERROR, String.format("Unknown demand mode %d", ctrl));
                break;
            }
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
        case "SetSensorType":
        {
            RevSpeedControllerSimWrapper wrapper = getMotorControllerWrapper(aCanPort);
            int type = aData.getInt();
            wrapper.setCanFeedbackDevice(type);
            break;
        }
        case "SetFeedbackDevice":
        {
            RevSpeedControllerSimWrapper wrapper = getMotorControllerWrapper(aCanPort);
            int type = aData.getInt();
            wrapper.setCanFeedbackDevice(type);
            break;
        }
        case "SetP":
        {
            RevSpeedControllerSimWrapper wrapper = getMotorControllerWrapper(aCanPort);
            int slot = aData.getInt();
            double value = aData.getFloat();
            wrapper.setPGain(slot, value);
            break;
        }
        case "SetI":
        {
            RevSpeedControllerSimWrapper wrapper = getMotorControllerWrapper(aCanPort);
            int slot = aData.getInt();
            double value = aData.getFloat();
            wrapper.setIGain(slot, value);
            break;
        }
        case "SetFF":
        {
            RevSpeedControllerSimWrapper wrapper = getMotorControllerWrapper(aCanPort);
            int slot = aData.getInt();
            double value = aData.getFloat();
            wrapper.setFGain(slot, value);
            break;
        }
        case "SetSmartMotionMaxVelocity":
        {
            RevSpeedControllerSimWrapper wrapper = getMotorControllerWrapper(aCanPort);
            aData.getInt();
            double value = aData.getFloat();
            wrapper.setMotionMagicMaxVelocity((int) value);
            break;
        }
        case "SetSmartMotionMaxAccel":
        {
            RevSpeedControllerSimWrapper wrapper = getMotorControllerWrapper(aCanPort);
            aData.getInt();
            double value = aData.getFloat();
            wrapper.setMotionMagicMaxAcceleration((int) value);
            break;
        }
        case "SetEncoderPosition":
        {
            int position = aData.getInt();
            RevSpeedControllerSimWrapper wrapper = getMotorControllerWrapper(aCanPort);
            wrapper.reset(position, wrapper.getVelocity(), wrapper.getCurrent());
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
        case "GetEncoderPosition":
        {
            RevSpeedControllerSimWrapper wrapper = getMotorControllerWrapper(aCanPort);

            float position = (float) wrapper.getPosition();
            aData.putFloat(0, position);
            break;
        }
        case "GetEncoderVelocity":
        {
            RevSpeedControllerSimWrapper wrapper = getMotorControllerWrapper(aCanPort);

            float velocity = (float) wrapper.getVelocity();
            aData.putFloat(0, velocity);
            break;
        }
        default:
            if (unsupportedFunctions.contains(aCallback))
            {
                sLOGGER.log(Level.DEBUG, "Unsupported option " + aCallback + "(" + aData.limit() + " bytes)");
            }
            else
            {
                sLOGGER.log(Level.WARN, "Unsupported option " + aCallback + "(" + aData.limit() + " bytes)");
            }
            break;
        }
    }

    protected void createSim(int aCanPort)
    {
        int simPort = aCanPort + BaseCanSmartSpeedController.sCAN_SC_OFFSET;
        if (!DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList().contains(simPort))
        {
            sLOGGER.log(Level.WARN, "REV Motor Controller is being created dynamically instead of in the config file for port " + aCanPort);

            DataAccessorFactory.getInstance().getSpeedControllerAccessor().createSimulator(simPort, RevSpeedControllerSimWrapper.class.getName());
        }
        else if (!(SensorActuatorRegistry.get().getSpeedControllers().get(simPort) instanceof RevSpeedControllerSimWrapper))
        {
            sLOGGER.log(Level.FATAL, "A pre-registered motor controller of type " + SensorActuatorRegistry.get().getSpeedControllers().get(simPort).getClass()
                + " is the wrong type on port " + aCanPort);
            SensorActuatorRegistry.get().getSpeedControllers().remove(simPort);
            createSim(aCanPort);
            return;
        }
        SensorActuatorRegistry.get().getSpeedControllers().get(simPort).setInitialized(true);
    }

    public void reset()
    {

    }

    protected <WrapperType> WrapperType getMotorControllerWrapper(int aCanPort)
    {
        return (WrapperType) SensorActuatorRegistry.get().getSpeedControllers().get(aCanPort + 100);
    }
}
