package com.snobot.simulator.simulator_components.rev;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.rev.BaseRevDeviceManager;
import com.snobot.simulator.simulator_components.ctre.CtreTalonSrxSpeedControllerSim;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

public class RevManager extends BaseRevDeviceManager
{
    private static final Logger sLOGGER = LogManager.getLogger(RevManager.class);

    @Override
    protected void createSim(int aCanPort)
    {
        if (!DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList()
                .contains(aCanPort + CtreTalonSrxSpeedControllerSim.sCTRE_OFFSET))
        {
            sLOGGER.log(Level.WARN, "REV Motor Controller is being created dynamically instead of in the config file for port " + aCanPort);

            DataAccessorFactory.getInstance().getSpeedControllerAccessor().createSimulator(aCanPort + CtreTalonSrxSpeedControllerSim.sCTRE_OFFSET,
                    RevSpeedControllerSimWrapper.class.getName());
        }
        SensorActuatorRegistry.get().getSpeedControllers().get(aCanPort + CtreTalonSrxSpeedControllerSim.sCTRE_OFFSET).setInitialized(true);
    }

    @Override
    protected void set(int deviceId, float setpoint, short auxSetpoint, byte pidSlot, byte rsvd)
    {
        super.set(deviceId, setpoint, auxSetpoint, pidSlot, rsvd);
        RevSpeedControllerSimWrapper wrapper = getMotorControllerWrapper(deviceId);
        wrapper.set(setpoint);
    }

    public void reset()
    {
        sLOGGER.log(Level.WARN, "UNSUPPORTED");
    }

    private RevSpeedControllerSimWrapper getMotorControllerWrapper(int aCanPort)
    {
        return (RevSpeedControllerSimWrapper) SensorActuatorRegistry.get().getSpeedControllers().get(aCanPort + 100);
    }
}
