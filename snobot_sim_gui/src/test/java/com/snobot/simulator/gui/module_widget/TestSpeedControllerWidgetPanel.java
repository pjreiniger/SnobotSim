package com.snobot.simulator.gui.module_widget;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseGuiSimulatorTest;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;

public class TestSpeedControllerWidgetPanel extends BaseGuiSimulatorTest
{
    @Test
    public void testPanel()
    {
        final SpeedController sc = new Victor(1);
        SpeedControllerGraphicDisplay panel = new SpeedControllerGraphicDisplay(
                DataAccessorFactory.getInstance().getSpeedControllerAccessor().getWrappers().keySet());

        JFrame frame = getFrame();
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);

        sleepForVisualDebugging();

        sc.set(-1);
        panel.update();
        panel.repaint();
        sleepForVisualDebugging();

        sc.set(.6);
        panel.update();
        panel.repaint();
        sleepForVisualDebugging();
    }

}
