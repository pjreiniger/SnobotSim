package com.snobot.simulator.gui.module_widget;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseGuiSimulatorTest;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;

public class TestRelayWidgetPanel extends BaseGuiSimulatorTest
{
    @Test
    public void testPanel()
    {
        final Relay relay = new Relay(1);
        RelayGraphicDisplay panel = new RelayGraphicDisplay(DataAccessorFactory.getInstance().getRelayAccessor().getPortList());

        JFrame frame = getFrame();
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);

        sleepForVisualDebugging();

        relay.set(Value.kForward);
        sleepForVisualDebugging();
        panel.update();
        panel.repaint();

        relay.set(Value.kReverse);
        sleepForVisualDebugging();
        panel.update();
        panel.repaint();

        relay.set(Value.kOn);
        sleepForVisualDebugging();
        panel.update();
        panel.repaint();

        relay.set(Value.kOff);
        sleepForVisualDebugging();
        panel.update();
        panel.repaint();
    }

}
