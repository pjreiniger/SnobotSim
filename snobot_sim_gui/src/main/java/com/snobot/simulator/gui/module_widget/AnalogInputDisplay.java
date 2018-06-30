package com.snobot.simulator.gui.module_widget;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Collection;
import java.util.Map.Entry;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import com.snobot.simulator.gui.Util;
import com.snobot.simulator.gui.module_widget.settings.SimpleSettingsDialog;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;


public class AnalogInputDisplay extends BaseWidgetDisplay<Integer, AnalogInDisplay>
{
    public AnalogInputDisplay(Collection<Integer> aKeys)
    {
        super(aKeys);
        setBorder(new TitledBorder("Analog"));
    }

    @Override
    public void update()
    {
        for (Entry<Integer, AnalogInDisplay> pair : mWidgetMap.entrySet())
        {
            double value = DataAccessorFactory.getInstance().getAnalogInAccessor().getVoltage(pair.getKey());
            pair.getValue().updateDisplay(value);
        }
    }

    @Override
    protected AnalogInDisplay createWidget(Integer aKey)
    {
        if (DataAccessorFactory.getInstance().getAnalogInAccessor().getWantsHidden(aKey))
        {
            return null;
        }
        return new AnalogInDisplay(aKey);
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

class AnalogInDisplay extends JPanel
{
    private final class CirclePanel extends JPanel
    {
        private static final int sDOT_SIZE = 30;

        private CirclePanel()
        {
            setPreferredSize(new Dimension(sDOT_SIZE, sDOT_SIZE));
        }

        @Override
        public void paint(Graphics aGraphics)
        {
            aGraphics.clearRect(0, 0, getWidth(), getHeight());
            aGraphics.setColor(Util.colorGetShadedColor(mVoltage, 5, 0));
            aGraphics.fillOval(0, 0, sDOT_SIZE, sDOT_SIZE);
        }
    }

    private final FocusListener mFocusListener = new FocusListener()
    {

        @Override
        public void focusLost(FocusEvent aEvent)
        {
            updateInput();
            mEditing = false;
        }

        @Override
        public void focusGained(FocusEvent aEvent)
        {
            mEditing = true;
        }
    };

    private final ActionListener mActionListener = new ActionListener()
    {

        @Override
        public void actionPerformed(ActionEvent aEvent)
        {
            updateInput();
        }

    };

    private final int mHandle;
    private final JTextField mVoltageTextField;

    private boolean mEditing;
    private double mVoltage;

    public AnalogInDisplay(int aHandle)
    {
        mHandle = aHandle;
        mVoltageTextField = new JTextField(6);
        mVoltageTextField.addFocusListener(mFocusListener);
        mVoltageTextField.addActionListener(mActionListener);
        CirclePanel circlePanel = new CirclePanel();

        add(mVoltageTextField);
        add(circlePanel);
    }

    public void updateDisplay(double aValue)
    {
        mVoltage = aValue;

        if (!mEditing)
        {
            mVoltageTextField.setText(Double.toString(aValue));
        }
    }

    private void updateInput()
    {
        try
        {
            double newVoltage = Double.parseDouble(mVoltageTextField.getText());
            if (newVoltage < 0)
            {
                newVoltage = 0;
            }

            if (newVoltage > 5)
            {
                newVoltage = 5;
            }

            DataAccessorFactory.getInstance().getAnalogInAccessor().setVoltage(mHandle, newVoltage);
        }
        catch (NumberFormatException ex)
        {
            LogManager.getLogger().log(Level.WARN, ex);
        }
    }
}
