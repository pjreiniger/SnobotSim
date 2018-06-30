package com.snobot.simulator;

import com.snobot.simulator.robot_container.IRobotClassContainer;

public interface ISimulatorUpdater
{

    public abstract void update();

    public abstract void setRobot(IRobotClassContainer aRobot);
}
