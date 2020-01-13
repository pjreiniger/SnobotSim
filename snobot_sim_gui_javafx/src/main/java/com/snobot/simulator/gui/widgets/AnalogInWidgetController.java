package com.snobot.simulator.gui.widgets;

import com.snobot.simulator.gui.Util;
import com.snobot.simulator.gui.widgets.settings.BasicSettingsDialog;
import com.snobot.simulator.gui.widgets.settings.DialogRunner;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;

public class AnalogInWidgetController extends BaseWidgetController
{
    @FXML
    private Label mLabel;

    @FXML
    private Circle mValueIcon;

    @FXML
    private TextField mValueField;

    private int mId;

    private boolean mEditing;

    @Override
    public void initialize(int aId)
    {
        mId = aId;
        mLabel.setText(getName());

        mValueField.focusedProperty().addListener((obs, oldVal, newVal) ->
        {
            System.out.println("Focus listener" + newVal);
            if (newVal)
            {
                mEditing = true;
            }
            else
            {
                handleUserSetting();
            }
        });
    }

    @Override
    public void update()
    {
        if (!mEditing)
        {
            double voltage = DataAccessorFactory.getInstance().getAnalogInAccessor().getVoltage(mId);

            mValueIcon.setFill(Util.colorGetShadedColor(voltage, 5, 0));
            mValueField.setText(Double.toString(voltage));
        }
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
        return DataAccessorFactory.getInstance().getAnalogInAccessor().getName(mId);
    }

    private void setName(String aName)
    {
        DataAccessorFactory.getInstance().getAnalogInAccessor().setName(mId, aName);
        mLabel.setText(aName);
    }

    @FXML
    public void handleAction()
    {
        handleUserSetting();
    }

    private void handleUserSetting()
    {
        mEditing = false;
        double newVoltage = Double.parseDouble(mValueField.getText());
        newVoltage = Math.min(5, newVoltage);
        newVoltage = Math.max(0, newVoltage);

        DataAccessorFactory.getInstance().getAnalogInAccessor().setVoltage(mId, newVoltage);

        mLabel.requestFocus();
    }

}
