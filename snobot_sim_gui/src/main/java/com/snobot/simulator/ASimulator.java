package com.snobot.simulator;

import com.snobot.simulator.robot_container.IRobotClassContainer;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

/**
 * Base class for a custom simulator.
 *
 * @author PJ
 *
 */
public class ASimulator implements ISimulatorUpdater
{
    private String mConfigFile;

    protected ASimulator()
    {
    }

    public boolean loadConfig(String aConfigFile)
    {
        mConfigFile = aConfigFile;
        return DataAccessorFactory.getInstance().getSimulatorDataAccessor().loadConfigFile(mConfigFile);
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
