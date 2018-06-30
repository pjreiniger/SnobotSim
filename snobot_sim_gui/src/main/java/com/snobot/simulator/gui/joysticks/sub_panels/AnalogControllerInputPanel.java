package com.snobot.simulator.gui.joysticks.sub_panels;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class AnalogControllerInputPanel extends JPanel
{
    private final JSlider mSlider;

    public AnalogControllerInputPanel(int aIndex)
    {
        mSlider = new JSlider(-127, 127);
        mSlider.setEnabled(false);
        JLabel label = new JLabel(Integer.toString(aIndex));

        add(label);
        add(mSlider);
    }

    public void setValue(double aValue)
    {
        mSlider.setValue((int) aValue);
    }
}
