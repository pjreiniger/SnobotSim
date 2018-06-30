package com.snobot.simulator.gui.module_widget;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseGuiSimulatorTest;

import edu.wpi.first.wpilibj.AnalogGyro;

public class TestGyroWidgetPanel extends BaseGuiSimulatorTest
{
    @Test
    public void testPanel()
    {
        AnalogGyro gyro = new AnalogGyro(1);
        GyroGraphicDisplay panel = new GyroGraphicDisplay(DataAccessorFactory.getInstance().getGyroAccessor().getPortList());

        JFrame frame = getFrame();
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);

        sleepForVisualDebugging();

        DataAccessorFactory.getInstance().getGyroAccessor().setAngle(1, 200);
        panel.update();
        panel.repaint();
        sleepForVisualDebugging();
    }

}
