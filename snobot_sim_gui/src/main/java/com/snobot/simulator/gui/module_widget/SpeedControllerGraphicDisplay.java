package com.snobot.simulator.gui.module_widget;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Collection;
import java.util.Map.Entry;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.snobot.simulator.gui.Util;
import com.snobot.simulator.gui.module_widget.settings.SpeedControllerSettingsDialog;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

public class SpeedControllerGraphicDisplay extends BaseWidgetDisplay<Integer, MotorDisplay>
{
    public SpeedControllerGraphicDisplay(Collection<Integer> aKeys)
    {
        super(aKeys);
        setBorder(new TitledBorder("Speed Controllers"));
    }

    @Override
    public void update()
    {
        for (Entry<Integer, MotorDisplay> pair : mWidgetMap.entrySet())
        {
            double value = DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVoltagePercentage(pair.getKey());
            pair.getValue().updateDisplay(value);
        }
    }

    @Override
    protected MotorDisplay createWidget(Integer aKey)
    {
        return new MotorDisplay();
    }

    @Override
    protected JDialog createSettingsDialog(Integer aKey)
    {
        SpeedControllerSettingsDialog dialog = new SpeedControllerSettingsDialog("Speed Controller " + aKey + " Settings", aKey, getName(aKey))
        {

            @Override
            protected void onSubmit()
            {
                super.onSubmit();

                DataAccessorFactory.getInstance().getSpeedControllerAccessor().setName(aKey, getComponentName());
                mLabelMap.get(aKey).setText(getComponentName());
            }

        };

        dialog.pack();

        return dialog;
    }

    @Override
    protected String getName(Integer aKey)
    {
        return DataAccessorFactory.getInstance().getSpeedControllerAccessor().getName(aKey);
    }
}

class MotorDisplay extends JPanel
{
    private static final int sDOT_SIZE = 30;

    private double mMotorSpeed;

    public MotorDisplay()
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
        aGraphics.setColor(Util.getMotorColor(mMotorSpeed));
        aGraphics.fillOval(0, 0, sDOT_SIZE, sDOT_SIZE);
    }
}
