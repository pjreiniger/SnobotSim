package com.snobot.simulator.gui.module_widget;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Collection;
import java.util.Map.Entry;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.snobot.simulator.gui.module_widget.settings.SimpleSettingsDialog;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

public class DigitalSourceGraphicDisplay extends BaseWidgetDisplay<Integer, DigitalSourceWrapperDisplay>
{
    public DigitalSourceGraphicDisplay(Collection<Integer> aKeys)
    {
        super(aKeys);
        setBorder(new TitledBorder("Digital IO"));
    }

    @Override
    public void update()
    {
        for (Entry<Integer, DigitalSourceWrapperDisplay> pair : mWidgetMap.entrySet())
        {
            boolean value = DataAccessorFactory.getInstance().getDigitalAccessor().getWrapper(pair.getKey()).get();
            pair.getValue().updateDisplay(value);
        }
    }

    @Override
    protected DigitalSourceWrapperDisplay createWidget(Integer aKey)
    {
        if (DataAccessorFactory.getInstance().getDigitalAccessor().getWrapper(aKey).getWantsHidden())
        {
            return null;
        }
        return new DigitalSourceWrapperDisplay();
    }

    @Override
    protected JDialog createSettingsDialog(Integer aKey)
    {
        SimpleSettingsDialog dialog = new SimpleSettingsDialog("Digital " + aKey + " Settings", aKey, getName(aKey))
        {
            @Override
            protected void onSubmit()
            {
                DataAccessorFactory.getInstance().getDigitalAccessor().getWrapper(aKey).setName(getComponentName());
                mLabelMap.get(aKey).setText(getComponentName());
            }
        };

        dialog.pack();

        return dialog;
    }

    @Override
    protected String getName(Integer aKey)
    {
        return DataAccessorFactory.getInstance().getDigitalAccessor().getWrapper(aKey).getName();
    }
}

class DigitalSourceWrapperDisplay extends JPanel
{
    private static final int sDOT_SIZE = 30;

    private boolean mState;

    public DigitalSourceWrapperDisplay()
    {
        setPreferredSize(new Dimension(sDOT_SIZE, sDOT_SIZE));
    }

    public void updateDisplay(boolean aValue)
    {
        mState = aValue;
    }

    @Override
    public void paint(Graphics aGraphics)
    {
        aGraphics.clearRect(0, 0, getWidth(), getHeight());
        aGraphics.setColor(mState ? Color.green : Color.red);
        aGraphics.fillOval(0, 0, sDOT_SIZE, sDOT_SIZE);
    }
}
