package com.snobot.simulator.robot_container;

import java.lang.reflect.InvocationTargetException;

/**
 * Wrapper around a python robot.
 *
 * @deprecated This isn't currently hooked up, because the Jython library is
 *             humongous and robotpy isn't updated yet
 *
 * @author PJ
 *
 */
public class PythonRobotContainer implements IRobotClassContainer
{
    protected static final String sCLASS_DELIMITER = "--";

    protected String mRobotName;
    // protected PythonInterpreter mInterpreter;
    // protected PyInstance mRobot;

    public PythonRobotContainer(String aConfig)
    {
        String path = aConfig.substring(0, aConfig.indexOf(sCLASS_DELIMITER));
        mRobotName = aConfig.substring(aConfig.indexOf(sCLASS_DELIMITER) + sCLASS_DELIMITER.length());

        // mInterpreter = new PythonInterpreter();
        // mInterpreter.exec("import sys\nsys.path.append('" + path + "')");
        // mInterpreter.exec("from robot import " + mRobotName + " as
        // SimRobot");
    }

    @Override
    public void constructRobot() throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException,
            SecurityException, IllegalArgumentException, InvocationTargetException
    {
        // mRobot = (PyInstance) mInterpreter.eval("SimRobot()");
    }

    @Override
    public void startCompetition()
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        // mRobot.invoke("startCompetition");
    }

    public static void main(String[] aArgs) throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException,
            SecurityException, IllegalArgumentException, InvocationTargetException
    {
        PythonRobotContainer container = new PythonRobotContainer("C:/Users/PJ/workspace/first_2017/PythonRobot--PjsTestRobot");
        container.constructRobot();
        container.startCompetition();
    }

}
