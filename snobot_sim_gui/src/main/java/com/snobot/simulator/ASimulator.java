package com.snobot.simulator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private static final Logger sLOGGER = LogManager.getLogger(ASimulator.class);

    private final SimulatorConfigReader mConfigReader;

    protected ASimulator()
    {
        mConfigReader = new SimulatorConfigReader();
    }

    public boolean loadConfig(String aConfigFile)
    {
        return mConfigReader.loadConfig(aConfigFile);
    }


    @Override
    public void update()
    {
        // Nothing to do
    }

    @Override
    public void setRobot(IRobotClassContainer aRobot)
    {
        // Nothing to do
    }
}
