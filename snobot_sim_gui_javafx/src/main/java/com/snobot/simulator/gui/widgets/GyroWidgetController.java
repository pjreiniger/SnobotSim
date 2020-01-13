package com.snobot.simulator.gui.widgets;

import java.text.DecimalFormat;

import com.snobot.simulator.gui.widgets.settings.BasicSettingsDialog;
import com.snobot.simulator.gui.widgets.settings.DialogRunner;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;

public class GyroWidgetController extends BaseWidgetController
{
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.###");

    @FXML
    private Label mLabel;

    @FXML
    private Circle mAngleIndicator;

    @FXML
    private Line mAngleArm;

    @FXML
    private TextField mAngleText;

    private Rotate mAngleArmRotation;

    private int mId;

    @Override
    public void initialize(int aId)
    {
        mId = aId;
        mLabel.setText(getName());

        mAngleArmRotation = new Rotate();
        mAngleArm.getTransforms().add(mAngleArmRotation);
    }

    @Override
    public void update()
    {
        double angle = DataAccessorFactory.getInstance().getGyroAccessor().getAngle(mId);
        mAngleText.setText(DECIMAL_FORMAT.format(angle));

        mAngleArmRotation.setAngle(angle);

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
        return DataAccessorFactory.getInstance().getGyroAccessor().getName(mId);
    }

    private void setName(String aName)
    {
        DataAccessorFactory.getInstance().getGyroAccessor().setName(mId, aName);
        mLabel.setText(aName);
    }
}
