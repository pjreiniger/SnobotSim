package com.snobot.simulator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.config.v1.SimulatorConfigReaderV1;
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

    private final SimulatorConfigReaderV1 mConfigReader;
    private String mConfigFile;

    protected ASimulator()
    {
        mConfigReader = new SimulatorConfigReaderV1();
    }

    public boolean loadConfig(String aConfigFile)
    {
        mConfigFile = aConfigFile;
        return mConfigReader.loadConfig(mConfigFile);
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

    public String getConfigFile()
    {
        return mConfigFile;
    }
}
