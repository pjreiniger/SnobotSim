package com.snobot.simulator.gui.widgets;

import java.text.DecimalFormat;

import com.snobot.simulator.gui.Util;
import com.snobot.simulator.gui.widgets.settings.DialogRunner;
import com.snobot.simulator.gui.widgets.settings.SpeedControllerSettingsDialog;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.simulator.wrapper_accessors.SpeedControllerWrapperAccessor.MotorSimType;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;

public class SpeedControllerWidgetController extends BaseWidgetController
{
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.###");

    @FXML
    private Label mLabel;

    @FXML
    private Circle mMotorSpeedIndicator;

    @FXML
    private TextField mValueField;

    private int mId;

    @Override
    public void initialize(int aId)
    {
        mId = aId;
        mLabel.setText(getName());
    }

    @Override
    public void update()
    {
        double speed = DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVoltagePercentage(mId);
        mMotorSpeedIndicator.setFill(Util.getMotorColor(speed));
        mValueField.setText(DECIMAL_FORMAT.format(speed));
    }

    @Override
    public void openSettings()
    {
        DialogRunner<SpeedControllerSettingsDialog> dialog = new DialogRunner<>("/com/snobot/simulator/gui/widgets/settings/speed_controller_settings.fxml");
        dialog.getController().setName(getName());

        MotorSimType mode = DataAccessorFactory.getInstance().getSpeedControllerAccessor().getMotorSimType(mId);
        dialog.getController().initialize(mId, mode);

        if (dialog.showAndWait())
        {
            setName(dialog.getController().getDisplayName());
            dialog.getController().saveMotorSim(mId);
            saveSettings();
        }
    }

    private String getName()
    {
        return DataAccessorFactory.getInstance().getSpeedControllerAccessor().getName(mId);
    }

    private void setName(String aName)
    {
        DataAccessorFactory.getInstance().getSpeedControllerAccessor().setName(mId, aName);
        mLabel.setText(aName);
    }
}
