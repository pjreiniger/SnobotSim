package com.snobot.simulator.gui.joysticks.sub_panels;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DigitalControllerInputPanel extends JPanel
{
    private final JCheckBox mCheckbox;

    public DigitalControllerInputPanel(int aIndex)
    {
        mCheckbox = new JCheckBox();
        mCheckbox.setEnabled(false);

        JLabel label = new JLabel(Integer.toString(aIndex));

        add(label);
        add(mCheckbox);
    }

    public void setValue(boolean aValue)
    {
        mCheckbox.setSelected(aValue);
    }
}
