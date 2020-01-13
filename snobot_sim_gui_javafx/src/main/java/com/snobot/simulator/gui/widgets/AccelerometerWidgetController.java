package com.snobot.simulator.gui.widgets;

import java.text.DecimalFormat;

import com.snobot.simulator.gui.widgets.settings.BasicSettingsDialog;
import com.snobot.simulator.gui.widgets.settings.DialogRunner;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;

public class AccelerometerWidgetController extends BaseWidgetController
{
    private static final double WIDTH = 80;
    private static final double GRAVITY_FPS = 32.2;
    private static final double GRAVITY_IPS = GRAVITY_FPS * 12;
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.###");

    @FXML
    private Label mLabel;

    @FXML
    private Rectangle mAccelerationBar;

    @FXML
    private TextField mAccelerationText;

    private int mId;

    private final double mMaxAcceleration;

    public AccelerometerWidgetController()
    {
        mMaxAcceleration = 2 * GRAVITY_IPS;
    }

    @Override
    public void initialize(int aId)
    {
        mId = aId;
        mLabel.setText(getName());
    }

    @Override
    public void update()
    {
        set(DataAccessorFactory.getInstance().getAccelerometerAccessor().getAcceleration(mId));
    }

    private void set(double aAcceleration)
    {
        mAccelerationText.setText(DECIMAL_FORMAT.format(aAcceleration));

        double acceleration = Math.min(mMaxAcceleration, aAcceleration);
        acceleration = Math.max(-mMaxAcceleration, acceleration);

        double width = acceleration * WIDTH / 2 / mMaxAcceleration;
        double x;
        if (acceleration < 0)
        {
            x = WIDTH / 2 + width;
        }
        else
        {
            x = WIDTH / 2;
        }

        mAccelerationBar.setWidth(Math.abs(width));
        mAccelerationBar.setX(x);
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
        return DataAccessorFactory.getInstance().getAccelerometerAccessor().getName(mId);
    }

    private void setName(String aName)
    {
        DataAccessorFactory.getInstance().getAccelerometerAccessor().setName(mId, aName);
        mLabel.setText(aName);
    }

}
