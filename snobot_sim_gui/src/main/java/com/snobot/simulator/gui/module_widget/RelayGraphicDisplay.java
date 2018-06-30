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

import edu.wpi.first.wpilibj.Relay.Value;

public class RelayGraphicDisplay extends BaseWidgetDisplay<Integer, RelayDisplay>
{
    public RelayGraphicDisplay(Collection<Integer> aKeys)
    {
        super(aKeys);
        setBorder(new TitledBorder("Relay"));
    }

    @Override
    public void update()
    {
        for (Entry<Integer, RelayDisplay> pair : mWidgetMap.entrySet())
        {
            int key = pair.getKey();
            boolean forward = DataAccessorFactory.getInstance().getRelayAccessor().getFowardValue(key);
            boolean reverse = DataAccessorFactory.getInstance().getRelayAccessor().getReverseValue(key);

            pair.getValue().updateDisplay(convertValue(forward, reverse));
        }
    }

    private Value convertValue(boolean aForwards, boolean aReverse)
    {
        if (aForwards && !aReverse)
        {
            return Value.kForward;
        }
        else if (!aForwards && aReverse)
        {
            return Value.kReverse;
        }
        else if (aForwards && aReverse)
        {
            return Value.kOn;
        }
        else
        {
            return Value.kOff;
        }
    }

    @Override
    protected RelayDisplay createWidget(Integer aKey)
    {
        return new RelayDisplay();
    }

    @Override
    protected JDialog createSettingsDialog(Integer aKey)
    {
        SimpleSettingsDialog dialog = new SimpleSettingsDialog("Relay " + aKey + " Settings", aKey, getName(aKey))
        {

            @Override
            protected void onSubmit()
            {
                DataAccessorFactory.getInstance().getRelayAccessor().setName(aKey, getComponentName());
                mLabelMap.get(aKey).setText(getComponentName());
            }

        };

        dialog.pack();

        return dialog;
    }

    @Override
    protected String getName(Integer aKey)
    {
        return DataAccessorFactory.getInstance().getRelayAccessor().getName(aKey);
    }
}

class RelayDisplay extends JPanel
{
    private static final int sWIDTH = 80;
    private static final int sHEIGHT = 15;

    private Value mValue;

    public RelayDisplay()
    {
        setPreferredSize(new Dimension(sWIDTH, sHEIGHT));
        mValue = Value.kOff;
    }

    public void updateDisplay(Value aValue)
    {
        mValue = aValue;
    }

    @Override
    public void paint(Graphics aGraphics)
    {
        aGraphics.clearRect(0, 0, getWidth(), getHeight());
        aGraphics.drawRect(0, 0, getWidth(), getHeight());

        switch (mValue)
        {
        case kOff:
            break;
        case kOn:
            aGraphics.setColor(Color.green);
            aGraphics.fillRect(0, 0, sWIDTH / 2, sHEIGHT);

            aGraphics.setColor(Color.red);
            aGraphics.fillRect(sWIDTH / 2, 0, sWIDTH, sHEIGHT);
            break;
        case kForward:
            aGraphics.setColor(Color.green);
            aGraphics.fillRect(0, 0, sWIDTH, sHEIGHT);
            break;
        case kReverse:
            aGraphics.setColor(Color.red);
            aGraphics.fillRect(0, 0, sWIDTH, sHEIGHT);
            break;
        default:
            break;
        }
    }
}
