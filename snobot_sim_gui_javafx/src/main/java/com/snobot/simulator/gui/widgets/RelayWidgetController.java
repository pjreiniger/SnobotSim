package com.snobot.simulator.gui.widgets;

import com.snobot.simulator.gui.widgets.settings.BasicSettingsDialog;
import com.snobot.simulator.gui.widgets.settings.DialogRunner;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RelayWidgetController extends BaseWidgetController
{
    @FXML
    private Label mLabel;

    @FXML
    private Rectangle mForwardIndicator;

    @FXML
    private Rectangle mReverseIndicator;

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
        boolean forward = DataAccessorFactory.getInstance().getRelayAccessor().getFowardValue(mId);
        boolean reverse = DataAccessorFactory.getInstance().getRelayAccessor().getReverseValue(mId);

        mForwardIndicator.setFill(forward ? Color.GREEN : Color.RED);
        mReverseIndicator.setFill(reverse ? Color.GREEN : Color.RED);
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
        return DataAccessorFactory.getInstance().getRelayAccessor().getName(mId);
    }

    private void setName(String aName)
    {
        DataAccessorFactory.getInstance().getRelayAccessor().setName(mId, aName);
        mLabel.setText(aName);
    }
}
