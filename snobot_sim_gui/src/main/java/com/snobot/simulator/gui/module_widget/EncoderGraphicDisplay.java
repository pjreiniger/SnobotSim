package com.snobot.simulator.gui.module_widget;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Map.Entry;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.snobot.simulator.gui.module_widget.settings.EncoderSettingsDialog;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

public class EncoderGraphicDisplay extends BaseWidgetDisplay<Integer, EncoderWrapperDisplay>
{
    public EncoderGraphicDisplay(Collection<Integer> aKeys, String aString)
    {
        super(aKeys);
        setBorder(new TitledBorder(aString));
    }

    @Override
    public void update()
    {
        for (Entry<Integer, EncoderWrapperDisplay> pair : mWidgetMap.entrySet())
        {
            int key = pair.getKey();
            boolean isConnected = DataAccessorFactory.getInstance().getEncoderAccessor().isHookedUp(key);
            double raw = -999;
            double distance = DataAccessorFactory.getInstance().getEncoderAccessor().getDistance(key);

            pair.getValue().updateDisplay(isConnected, raw, distance);
        }
    }

    @Override
    protected EncoderWrapperDisplay createWidget(Integer aPair)
    {
        return new EncoderWrapperDisplay();
    }

    @Override
    protected JDialog createSettingsDialog(Integer aKey)
    {

        EncoderSettingsDialog dialog = new EncoderSettingsDialog("Encoder " + aKey + " Settings", aKey, getName(aKey))
        {

            @Override
            protected void onSubmit()
            {
                super.onSubmit();

                DataAccessorFactory.getInstance().getEncoderAccessor().setName(aKey, getComponentName());
                mLabelMap.get(aKey).setText(getComponentName());
            }

        };

        dialog.pack();

        return dialog;
    }

    @Override
    protected String getName(Integer aKey)
    {
        return DataAccessorFactory.getInstance().getEncoderAccessor().getName(aKey);
    }
}

class EncoderWrapperDisplay extends JPanel
{
    private final JTextField mRawField;
    private final JTextField mDistanceField;

    public EncoderWrapperDisplay()
    {
        mRawField = new JTextField(6);
        mDistanceField = new JTextField(6);
        add(mRawField);
        add(mDistanceField);
    }

    public void updateDisplay(boolean aHasConnection, double aRaw, double aDistance)
    {
        if (aHasConnection)
        {
            DecimalFormat df = new DecimalFormat("#.###");
            mRawField.setText(Double.toString(aRaw));
            mDistanceField.setText(df.format(aDistance));
        }
        else
        {
            mRawField.setText("Not Hooked Up");
            mDistanceField.setText("Not Hooked Up");
        }
    }
}
