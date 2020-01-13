package com.snobot.simulator.gui.joysticks.sub_panels;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class AnalogInputPanelController
{
    @FXML
    private Label mLabel;
    @FXML
    private Slider mSlider;


    public void setValue(double aValue)
    {
        mSlider.setValue(aValue);
    }

    public void setName(String aText)
    {
        mLabel.setText(aText);
    }
}
