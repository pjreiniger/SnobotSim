package com.snobot.simulator.gui.module_widget;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Map.Entry;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.snobot.simulator.gui.module_widget.settings.EncoderSettingsDialog;
import com.snobot.simulator.module_wrapper.interfaces.IEncoderWrapper;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

public class EncoderGraphicDisplay extends BaseWidgetDisplay<Integer, EncoderWrapperDisplay>
{
    public EncoderGraphicDisplay(Collection<Integer> aKeys)
    {
        super(aKeys);
        setBorder(new TitledBorder("Encoders"));
    }

    @Override
    public void update()
    {
        for (Entry<Integer, EncoderWrapperDisplay> pair : mWidgetMap.entrySet())
        {
            int key = pair.getKey();
            IEncoderWrapper wrapper = DataAccessorFactory.getInstance().getEncoderAccessor().getWrapper(key);
            boolean isConnected = wrapper.isHookedUp();
            double distance = wrapper.getPosition();

            pair.getValue().updateDisplay(isConnected, distance);
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

                DataAccessorFactory.getInstance().getEncoderAccessor().getWrapper(aKey).setName(getComponentName());
                mLabelMap.get(aKey).setText(getComponentName());
            }

        };

        dialog.pack();

        return dialog;
    }

    @Override
    protected String getName(Integer aKey)
    {
        return DataAccessorFactory.getInstance().getEncoderAccessor().getWrapper(aKey).getName();
    }
}

class EncoderWrapperDisplay extends JPanel
{
    private final JTextField mDistanceField;

    public EncoderWrapperDisplay()
    {
        mDistanceField = new JTextField(6);
        mDistanceField.setEnabled(false);
        add(mDistanceField);
    }

    public void updateDisplay(boolean aHasConnection, double aDistance)
    {
        if (aHasConnection)
        {
            DecimalFormat df = new DecimalFormat("#.###");
            mDistanceField.setText(df.format(aDistance));
        }
        else
        {
            mDistanceField.setText("Not Hooked Up");
        }
    }
}
