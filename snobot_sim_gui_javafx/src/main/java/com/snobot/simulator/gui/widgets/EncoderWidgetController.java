package com.snobot.simulator.gui.widgets;

import java.text.DecimalFormat;

import com.snobot.simulator.gui.widgets.settings.DialogRunner;
import com.snobot.simulator.gui.widgets.settings.EncoderSettingsDialog;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EncoderWidgetController extends BaseWidgetController
{
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.###");

    @FXML
    private Label mLabel;

    @FXML
    private TextField mDistanceField;

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
        boolean isConnected = DataAccessorFactory.getInstance().getEncoderAccessor().isHookedUp(mId);
        if (isConnected)
        {
            mDistanceField.setText(DECIMAL_FORMAT.format(DataAccessorFactory.getInstance().getEncoderAccessor().getDistance(mId)));
        }
        else
        {
            mDistanceField.setText("No SC Connected");
        }
    }

    @Override
    public void openSettings()
    {
        DialogRunner<EncoderSettingsDialog> dialog = new DialogRunner<>("/com/snobot/simulator/gui/widgets/settings/encoder_settings.fxml");
        dialog.getController().setName(getName());
        dialog.getController().setEncoderHandle(mId);
        if (dialog.showAndWait())
        {
            setName(dialog.getController().getDisplayName());
            DataAccessorFactory.getInstance().getEncoderAccessor().connectSpeedController(mId, dialog.getController().getSelectedId());
            saveSettings();
        }
    }

    private String getName()
    {
        return DataAccessorFactory.getInstance().getEncoderAccessor().getName(mId);
    }

    private void setName(String aName)
    {
        DataAccessorFactory.getInstance().getEncoderAccessor().setName(mId, aName);
        mLabel.setText(aName);
    }
}
