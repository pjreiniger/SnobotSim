package com.snobot.simulator.gui;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import com.snobot.test.utilities.BaseGuiSimulatorTest;

import edu.wpi.first.wpilibj.AnalogAccelerometer;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;

public class TestGuiModulePanels extends BaseGuiSimulatorTest
{
    @Test
    public void testGuiPanels()
    {
        new Talon(0);
        new Solenoid(0);
        new Relay(0);
        new Encoder(0, 1);
        new AnalogPotentiometer(0);
        new AnalogGyro(1);
        new AnalogAccelerometer(2);
        new AnalogInput(3);
        new DigitalInput(2);

        GraphicalSensorDisplayPanel panel = new GraphicalSensorDisplayPanel();
        // panel.create();
        // panel.update();

        JFrame frame = getFrame();
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

        panel.repaint();
    }
}
