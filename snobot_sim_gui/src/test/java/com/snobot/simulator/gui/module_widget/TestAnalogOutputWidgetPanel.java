package com.snobot.simulator.gui.module_widget;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseGuiSimulatorTest;

import edu.wpi.first.wpilibj.AnalogInput;

public class TestAnalogOutputWidgetPanel extends BaseGuiSimulatorTest
{
    @Test
    public void testPanel()
    {
        AnalogInput accel = new AnalogInput(1);
        AnalogOutputDisplay panel = new AnalogOutputDisplay(DataAccessorFactory.getInstance().getAnalogInAccessor().getWrappers().keySet());

        JFrame frame = getFrame();
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);

        panel.update();
        panel.repaint();
        sleepForVisualDebugging();
    }

}
