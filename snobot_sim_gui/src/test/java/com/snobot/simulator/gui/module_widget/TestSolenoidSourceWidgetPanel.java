package com.snobot.simulator.gui.module_widget;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseGuiSimulatorTest;

import edu.wpi.first.wpilibj.Solenoid;

public class TestSolenoidSourceWidgetPanel extends BaseGuiSimulatorTest
{
    @Test
    public void testPanel()
    {
        final Solenoid solenoid = new Solenoid(1);
        SolenoidGraphicDisplay panel = new SolenoidGraphicDisplay(DataAccessorFactory.getInstance().getSolenoidAccessor().getWrappers().keySet());

        JFrame frame = getFrame();
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);

        sleepForVisualDebugging();

        solenoid.set(true);
        panel.update();
        panel.repaint();
        sleepForVisualDebugging();
    }

}
