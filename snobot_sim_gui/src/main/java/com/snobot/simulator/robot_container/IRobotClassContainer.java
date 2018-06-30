package com.snobot.simulator.robot_container;

import java.lang.reflect.InvocationTargetException;

/**
 * Interface that provides a wrapper around a robot class
 *
 * @author PJ
 *
 */
public interface IRobotClassContainer
{
    public void constructRobot()
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, SecurityException,
            IllegalArgumentException, InvocationTargetException;

    public void startCompetition()
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException;
}
