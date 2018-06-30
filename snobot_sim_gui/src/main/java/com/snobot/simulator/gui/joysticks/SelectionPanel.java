package com.snobot.simulator.gui.joysticks;

import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.snobot.simulator.joysticks.IMockJoystick;
import com.snobot.simulator.joysticks.JoystickFactory;
import com.snobot.simulator.joysticks.joystick_specializations.NullJoystick;

import edu.wpi.first.wpilibj.DriverStation;

public class SelectionPanel extends JPanel
{
    private static class SinglePanel extends JPanel
    {
        private final int mJoystickIndex;
        private final JComboBox<String> mComboBox;

        public SinglePanel(int aIndex)
        {
            mJoystickIndex = aIndex;

            mComboBox = new JComboBox<>();
            mComboBox.setFocusable(false);

            JLabel label = new JLabel("Joystick " + aIndex);

            add(label);
            add(mComboBox);
        }

        public void setNames(Collection<String> aControllerNames)
        {
            mComboBox.removeAllItems();
            mComboBox.addItem(NullJoystick.sNAME);
            for (String name : aControllerNames)
            {
                mComboBox.addItem(name);
            }
        }

        public void setSelectedItem(Object aName)
        {
            mComboBox.setSelectedItem(aName);
        }
    }

    private final List<SinglePanel> mPanels = new ArrayList<>();

    public SelectionPanel(Collection<String> aControllerNames, IMockJoystick[] aSelectedJoysticks) // NOPMD
    {
        setLayout(new GridLayout(0, 1, 0, 0));

        for (int i = 0; i < DriverStation.kJoystickPorts; ++i)
        {
            SinglePanel panel = new SinglePanel(i);
            mPanels.add(panel);
            add(panel);

            panel.setNames(aControllerNames);
            panel.setSelectedItem(aSelectedJoysticks[i].getName());

            panel.mComboBox.addItemListener(new ItemListener()
            {

                @Override
                public void itemStateChanged(ItemEvent aEvent)
                {
                    if (aEvent.getStateChange() == ItemEvent.SELECTED)
                    {
                        JoystickFactory.getInstance().setJoysticks(panel.mJoystickIndex, aEvent.getItem().toString());
                    }
                }
            });
        }
    }

}
