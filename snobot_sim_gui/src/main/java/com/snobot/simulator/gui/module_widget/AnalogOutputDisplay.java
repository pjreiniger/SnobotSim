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


public class AnalogOutputDisplay extends BaseWidgetDisplay<Integer, AnalogOutDisplay>
{
    public AnalogOutputDisplay(Collection<Integer> aKeys)
    {
        super(aKeys);
        setBorder(new TitledBorder("Analog Output"));
    }

    @Override
    public void update()
    {
        for (Entry<Integer, AnalogOutDisplay> pair : mWidgetMap.entrySet())
        {
            double value = DataAccessorFactory.getInstance().getAnalogInAccessor().getVoltage(pair.getKey());
            pair.getValue().updateDisplay(value);
        }
    }

    @Override
    protected AnalogOutDisplay createWidget(Integer aKey)
    {
        if (DataAccessorFactory.getInstance().getAnalogInAccessor().getWantsHidden(aKey))
        {
            return null;
        }
        return new AnalogOutDisplay();
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

class AnalogOutDisplay extends JPanel
{
    private static final int sDOT_SIZE = 30;

    private double mVoltage;

    public AnalogOutDisplay()
    {
        setPreferredSize(new Dimension(sDOT_SIZE, sDOT_SIZE));
    }

    public void updateDisplay(double aValue)
    {
        mVoltage = aValue;
    }

    @Override
    public void paint(Graphics aGraphics)
    {
        aGraphics.clearRect(0, 0, getWidth(), getHeight());
        aGraphics.setColor(Util.colorGetShadedColor(mVoltage, 5, 0));
        aGraphics.fillOval(0, 0, sDOT_SIZE, sDOT_SIZE);
    }
}
