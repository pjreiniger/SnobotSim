package com.snobot.simulator.gui.module_widget;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Collection;
import java.util.Map.Entry;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.snobot.simulator.gui.Util;
import com.snobot.simulator.gui.module_widget.settings.SimpleSettingsDialog;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;


public class AnalogOutputDisplay extends BaseWidgetDisplay<Integer, AnalogDisplay>
{
    public AnalogOutputDisplay(Collection<Integer> aKeys)
    {
        super(aKeys);
        setBorder(new TitledBorder("Analog"));
    }

    @Override
    public void update()
    {
        for (Entry<Integer, AnalogDisplay> pair : mWidgetMap.entrySet())
        {
            double value = DataAccessorFactory.getInstance().getAnalogInAccessor().getVoltage(pair.getKey());
            pair.getValue().updateDisplay(value);
        }
    }

    @Override
    protected AnalogDisplay createWidget(Integer aKey)
    {
        if (DataAccessorFactory.getInstance().getAnalogInAccessor().getWantsHidden(aKey))
        {
            return null;
        }
        return new AnalogDisplay();
    }

    @Override
    protected JDialog createSettingsDialog(Integer aKey)
    {
        SimpleSettingsDialog dialog = new SimpleSettingsDialog("Analog " + aKey + " Settings", aKey, getName(aKey))
        {

            @Override
            protected void onSubmit()
            {
                DataAccessorFactory.getInstance().getAnalogInAccessor().setName(aKey, getComponentName());
                mLabelMap.get(aKey).setText(getComponentName());
            }

        };

        dialog.pack();

        return dialog;
    }

    @Override
    protected String getName(Integer aKey)
    {
        return DataAccessorFactory.getInstance().getAnalogInAccessor().getName(aKey);
    }
}

class AnalogDisplay extends JPanel
{
    private static final int sDOT_SIZE = 30;

    private double mMotorSpeed;

    public AnalogDisplay()
    {
        setPreferredSize(new Dimension(sDOT_SIZE, sDOT_SIZE));
    }

    public void updateDisplay(double aValue)
    {
        mMotorSpeed = aValue;
    }

    @Override
    public void paint(Graphics aGraphics)
    {
        aGraphics.clearRect(0, 0, getWidth(), getHeight());
        aGraphics.setColor(Util.colorGetShadedColor(mMotorSpeed, 5, 0));
        aGraphics.fillOval(0, 0, sDOT_SIZE, sDOT_SIZE);
    }
}
