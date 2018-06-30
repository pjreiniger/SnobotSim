package com.snobot.simulator.gui.module_widget;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseGuiSimulatorTest;

import edu.wpi.first.wpilibj.Encoder;

public class TestEncoderWidgetPanel extends BaseGuiSimulatorTest
{
    @Test
    public void testPanel()
    {
        Encoder encoder = new Encoder(1, 2);
        EncoderGraphicDisplay panel = new EncoderGraphicDisplay(DataAccessorFactory.getInstance().getEncoderAccessor().getPortList());

        JFrame frame = getFrame();
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);

        panel.update();
        panel.repaint();
        sleepForVisualDebugging();
    }

}
