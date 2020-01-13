package com.snobot.simulator.gui.widgets;

import java.text.DecimalFormat;

import com.snobot.simulator.gui.Util;
import com.snobot.simulator.gui.widgets.settings.BasicSettingsDialog;
import com.snobot.simulator.gui.widgets.settings.DialogRunner;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;

public class AnalogOutWidgetController extends BaseWidgetController
{
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.###");

    @FXML
    private Label mLabel;

    @FXML
    private Circle mValueIcon;

    @FXML
    private TextField mValueField;

    private int mId;

    @Override
    public void initialize(int aId)
    {
        mId = aId;
        mLabel.setText(getName());
        mValueField.setEditable(false);
    }

    @Override
    public void update()
    {
        double voltage = DataAccessorFactory.getInstance().getAnalogOutAccessor().getVoltage(mId);
        mValueIcon.setFill(Util.colorGetShadedColor(voltage, 5, 0));
        mValueField.setText(DECIMAL_FORMAT.format(voltage));

    }

    @Override
    public void openSettings()
    {
        DialogRunner<BasicSettingsDialog> dialog = new DialogRunner<>("/com/snobot/simulator/gui/widgets/settings/basic_settings.fxml");
        dialog.getController().setName(getName());
        if (dialog.showAndWait())
        {
            setName(dialog.getController().getDisplayName());
            saveSettings();
        }
    }

    private String getName()
    {
        return DataAccessorFactory.getInstance().getAnalogOutAccessor().getName(mId);
    }

    private void setName(String aName)
    {
        DataAccessorFactory.getInstance().getAnalogOutAccessor().setName(mId, aName);
        mLabel.setText(aName);
    }
}
