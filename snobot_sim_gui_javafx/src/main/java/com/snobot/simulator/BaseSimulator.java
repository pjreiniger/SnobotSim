package com.snobot.simulator;

import com.snobot.simulator.config.v1.SimulatorConfigReaderV1;
import com.snobot.simulator.robot_container.IRobotClassContainer;

/**
 * Base class for a custom simulator.
 *
 * @author PJ
 *
 */
public class BaseSimulator implements ISimulatorUpdater
{
    private final SimulatorConfigReaderV1 mConfigReader;
    private String mConfigFile;

    protected BaseSimulator()
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
