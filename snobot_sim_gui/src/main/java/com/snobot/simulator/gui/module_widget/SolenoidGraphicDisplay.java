package com.snobot.simulator.gui.module_widget;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Collection;
import java.util.Map.Entry;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.snobot.simulator.gui.module_widget.settings.SimpleSettingsDialog;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

public class SolenoidGraphicDisplay extends BaseWidgetDisplay<Integer, SolenoidDisplay>
{
    public SolenoidGraphicDisplay(Collection<Integer> aKeys)
    {
        super(aKeys);
        setBorder(new TitledBorder("Solenoid"));
    }

    @Override
    public void update()
    {
        for (Entry<Integer, SolenoidDisplay> pair : mWidgetMap.entrySet())
        {
            if (pair.getValue().isVisible())
            {
                boolean state = DataAccessorFactory.getInstance().getSolenoidAccessor().getWrapper(pair.getKey()).get();
                pair.getValue().updateDisplay(state);
            }
        }
    }

    @Override
    protected SolenoidDisplay createWidget(Integer aKey)
    {
        return new SolenoidDisplay();
    }

    @Override
    protected JDialog createSettingsDialog(Integer aKey)
    {
        SimpleSettingsDialog dialog = new SimpleSettingsDialog("Solenoid " + aKey + " Settings", aKey, getName(aKey))
        {

            @Override
            protected void onSubmit()
            {
                DataAccessorFactory.getInstance().getSolenoidAccessor().getWrapper(aKey).setName(getComponentName());
                mLabelMap.get(aKey).setText(getComponentName());
            }
        };

        dialog.pack();

        return dialog;
    }

    @Override
    protected String getName(Integer aKey)
    {
        return DataAccessorFactory.getInstance().getSolenoidAccessor().getWrapper(aKey).getName();
    }
}

class SolenoidDisplay extends JPanel
{
    private static final int sWIDTH = 80;
    private static final int sHEIGHT = 15;
    private static final int sCYLINDER_WIDTH = sWIDTH / 2;

    private static final int sPOLE_SIZE = 2;
    private static final int sPLUNGER_SIZE = 5;
    private static final int sSHORT_POLE_WIDTH = 5;

    private boolean mState;

    public SolenoidDisplay()
    {
        setPreferredSize(new Dimension(sWIDTH, sHEIGHT));
    }

    public void updateDisplay(boolean aValue)
    {
        mState = aValue;
    }

    @Override
    public void paint(Graphics aGraphics)
    {
        aGraphics.clearRect(0, 0, getWidth(), getHeight());

        aGraphics.fillRect(0, 0, sCYLINDER_WIDTH, sHEIGHT);

        if (mState)
        {
            aGraphics.fillRect(sCYLINDER_WIDTH, sHEIGHT / 2, sWIDTH, sPOLE_SIZE);
            aGraphics.fillRect(sWIDTH - sPLUNGER_SIZE, 0, sPLUNGER_SIZE, sHEIGHT);
        }
        else
        {
            aGraphics.fillRect(sCYLINDER_WIDTH, sHEIGHT / 2, sSHORT_POLE_WIDTH, sPOLE_SIZE);
            aGraphics.fillRect(sCYLINDER_WIDTH + sSHORT_POLE_WIDTH, 0, sPLUNGER_SIZE, sHEIGHT);
        }
    }
}
