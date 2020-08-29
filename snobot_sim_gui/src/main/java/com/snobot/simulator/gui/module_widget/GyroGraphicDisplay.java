package com.snobot.simulator.gui.module_widget;

import java.util.Collection;
import java.util.Map.Entry;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.snobot.simulator.gui.module_widget.settings.GyroSettingsDialog;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

public class GyroGraphicDisplay extends BaseWidgetDisplay<Integer, GyroWrapperDisplay>
{
    public GyroGraphicDisplay(Collection<Integer> aKeys)
    {
        super(aKeys);
        setBorder(new TitledBorder("Gyros"));
    }

    @Override
    public void update()
    {
        for (Entry<Integer, GyroWrapperDisplay> pair : mWidgetMap.entrySet())
        {
            int key = pair.getKey();
            double angle = DataAccessorFactory.getInstance().getGyroAccessor().getWrapper(key).getAngle();

            pair.getValue().updateDisplay(angle);
        }
    }

    @Override
    protected GyroWrapperDisplay createWidget(Integer aPair)
    {
        return new GyroWrapperDisplay();
    }

    @Override
    protected JDialog createSettingsDialog(Integer aKey)
    {
        GyroSettingsDialog dialog = new GyroSettingsDialog("Gyro " + aKey + " Settings", aKey, getName(aKey))
        {
            @Override
            protected void onSubmit()
            {
                super.onSubmit();

                DataAccessorFactory.getInstance().getGyroAccessor().getWrapper(aKey).setName(getComponentName());
                mLabelMap.get(aKey).setText(getComponentName());
            }
        };

        dialog.pack();

        return dialog;
    }

    @Override
    protected String getName(Integer aKey)
    {
        return DataAccessorFactory.getInstance().getGyroAccessor().getWrapper(aKey).getName();
    }
}

class GyroWrapperDisplay extends JPanel
{
    private final JTextField mAngleField;

    public GyroWrapperDisplay()
    {
        mAngleField = new JTextField(6);
        mAngleField.setEnabled(false);
        add(mAngleField);
    }

    public void updateDisplay(double aAngle)
    {
        mAngleField.setText(Double.toString(aAngle));
    }
}
