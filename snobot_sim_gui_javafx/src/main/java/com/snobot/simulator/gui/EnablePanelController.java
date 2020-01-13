package com.snobot.simulator.gui;

import java.text.DecimalFormat;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

import edu.wpi.first.wpilibj.DriverStation;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

public class EnablePanelController
{
    private static final DecimalFormat MATCH_TIME_FORMAT = new DecimalFormat("000.00");

    @FXML
    private Label mMatchTime;

    @FXML
    private CheckBox mEnableButton;

    @FXML
    private CheckBox mAutonButton;

    public void setUseSnobotSim(boolean aUseSnobotSimDriverstation)
    {
        mEnableButton.setDisable(!aUseSnobotSimDriverstation);
        mAutonButton.setDisable(!aUseSnobotSimDriverstation);
    }

    public void setTime(double aTime)
    {
        mMatchTime.setText(MATCH_TIME_FORMAT.format(aTime));
    }

    public void updateLoop(boolean aUseSnobotSimDriverstation)
    {

        if (aUseSnobotSimDriverstation)
        {
            setTime(DataAccessorFactory.getInstance().getDriverStationAccessor().getTimeSinceEnabled());
        }
        else
        {
            setTime(DriverStation.getInstance().getMatchTime());
            mEnableButton.setSelected(DriverStation.getInstance().isEnabled());
            mAutonButton.setSelected(DriverStation.getInstance().isAutonomous());
        }
    }
}
