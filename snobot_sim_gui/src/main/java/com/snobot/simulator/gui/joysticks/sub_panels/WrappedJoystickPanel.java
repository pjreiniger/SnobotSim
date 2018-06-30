package com.snobot.simulator.gui.joysticks.sub_panels;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.joysticks.IMockJoystick;

public class WrappedJoystickPanel extends JPanel
{
    private static final Logger sLOGGER = LogManager.getLogger(WrappedJoystickPanel.class);

    private IMockJoystick mJoystick;
    private JPanel mDigitalPanel;
    private JPanel mAnalogPanel;
    private List<AnalogControllerInputPanel> mAnalogDisplays;
    private List<DigitalControllerInputPanel> mDigitalDisplays;

    public WrappedJoystickPanel()
    {
        this(null);
    }

    public WrappedJoystickPanel(IMockJoystick aJoystick)
    {
        initComponents();
        setBackground(Color.green);

        setJoystick(aJoystick);
    }

    public final void setJoystick(IMockJoystick aJoystick)
    {
        mJoystick = aJoystick;
        mAnalogPanel.removeAll();
        mDigitalPanel.removeAll();

        if (mJoystick != null)
        {
            mAnalogDisplays = new ArrayList<>();
            mDigitalDisplays = new ArrayList<>();

            for (int i = 0; i < mJoystick.getAxisCount(); ++i)
            {
                AnalogControllerInputPanel panel = new AnalogControllerInputPanel(i);
                mAnalogDisplays.add(panel);
                mAnalogPanel.add(panel);
            }
            for (int i = 0; i < mJoystick.getButtonCount(); ++i)
            {
                DigitalControllerInputPanel panel = new DigitalControllerInputPanel(i);
                mDigitalDisplays.add(panel);
                mDigitalPanel.add(panel);
            }
        }

        repaint();
    }

    public void updateDisplay()
    {
        if (mJoystick == null)
        {
            sLOGGER.log(Level.WARN, "Joystick is null");
        }
        else
        {
            float[] axisValues = mJoystick.getAxisValues();
            int buttonMask = mJoystick.getButtonMask();

            for (int i = 0; i < axisValues.length; ++i)
            {
                AnalogControllerInputPanel panel = mAnalogDisplays.get(i);
                panel.setValue(axisValues[i]);
            }
            for (int i = 0; i < mJoystick.getButtonCount(); ++i)
            {
                DigitalControllerInputPanel panel = mDigitalDisplays.get(i);
                boolean active = (buttonMask & (1 << i)) != 0;
                panel.setValue(active);
            }
        }
    }

    private void initComponents()
    {
        mDigitalPanel = new JPanel();
        mAnalogPanel = new JPanel();

        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                .addComponent(mAnalogPanel, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE).addComponent(mDigitalPanel,
                                        GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE))
                        .addGap(0)));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup().addComponent(mAnalogPanel, GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                        .addPreferredGap(ComponentPlacement.RELATED).addComponent(mDigitalPanel, GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                        .addGap(0)));
        setLayout(groupLayout);
    }

}
