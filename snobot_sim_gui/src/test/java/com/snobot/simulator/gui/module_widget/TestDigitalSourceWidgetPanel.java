package com.snobot.simulator.gui.module_widget;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseGuiSimulatorTest;

import edu.wpi.first.wpilibj.DigitalInput;

public class TestDigitalSourceWidgetPanel extends BaseGuiSimulatorTest
{
    @Test
    public void testPanel()
    {
        DigitalInput dio = new DigitalInput(1);
        DigitalSourceGraphicDisplay panel = new DigitalSourceGraphicDisplay(DataAccessorFactory.getInstance().getDigitalAccessor().getWrappers().keySet());

        JFrame frame = getFrame();
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);

        sleepForVisualDebugging();

        DataAccessorFactory.getInstance().getDigitalAccessor().getWrapper(1).set(true);
        panel.update();
        panel.repaint();
        sleepForVisualDebugging();
    }

}
