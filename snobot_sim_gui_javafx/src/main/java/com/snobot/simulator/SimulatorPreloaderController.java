package com.snobot.simulator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;

public class SimulatorPreloaderController
{
    @FXML
    private Pane mRoot;
    @FXML
    private Pane mBackgroundContainer;
    @FXML
    private Label mVersionLabel;
    @FXML
    private Label mStateLabel;
    @FXML
    private ProgressBar mProgressBar;

    @FXML
    private void initialize()
    {
        mProgressBar.setProgress(-1);
        mVersionLabel.setText(SnobotSimGuiVersion.Version);
    }

    public void setStateText(String aText)
    {
        mStateLabel.setText(aText);
    }

    public void setProgress(double aProgress)
    {
        mProgressBar.setProgress(aProgress);
    }

}
