package com.snobot.simulator;

import org.apache.log4j.Logger;

import com.snobot.simulator.config.SimulatorConfigReader;
import com.snobot.simulator.robot_container.IRobotClassContainer;

/**
 * Base class for a custom simulator.
 * 
 * @author PJ
 *
 */
public class ASimulator implements ISimulatorUpdater
{
    private static final Logger sLOGGER = Logger.getLogger(ASimulator.class);

    private final SimulatorConfigReader mConfigReader;

    protected ASimulator()
    {
        mConfigReader = new SimulatorConfigReader();
    }

    public boolean loadConfig(String aConfigFile)
    {
        boolean success = mConfigReader.loadConfig(aConfigFile);

        return success;
    }


    @Override
    public void update()
    {

    }

    @Override
    public void setRobot(IRobotClassContainer aRobot)
    {

    }

    public void createSimulatorComponents()
    {
        mConfigReader.setupSimulatorComponents();
    }
}
