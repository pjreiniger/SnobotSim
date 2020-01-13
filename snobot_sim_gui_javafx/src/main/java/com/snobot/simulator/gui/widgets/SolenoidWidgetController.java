package com.snobot.simulator.gui.widgets;

import com.snobot.simulator.gui.widgets.settings.BasicSettingsDialog;
import com.snobot.simulator.gui.widgets.settings.DialogRunner;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;

public class SolenoidWidgetController extends BaseWidgetController
{
    @FXML
    private Label mLabel;

    @FXML
    private Rectangle mPole;

    @FXML
    private Rectangle mPlunger;

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
        boolean state = DataAccessorFactory.getInstance().getSolenoidAccessor().get(mId);
        set(state);
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
        return DataAccessorFactory.getInstance().getSolenoidAccessor().getName(mId);
    }

    private void setName(String aName)
    {
        DataAccessorFactory.getInstance().getSolenoidAccessor().setName(mId, aName);
        mLabel.setText(aName);
    }

    private void set(boolean aState)
    {
        if (aState)
        {
            mPole.setX(30);
            mPlunger.setX(80);
        }
        else
        {
            mPole.setX(0);
            mPlunger.setX(50);
        }
    }

}
