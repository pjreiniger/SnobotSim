package com.snobot.simulator.gui.joysticks.sub_panels;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

public class DigitalInputPanelController
{
    @FXML
    protected CheckBox mCheckbox;

    public void setValue(boolean aValue)
    {
        mCheckbox.setSelected(aValue);
    }

    public void setName(String aText)
    {
        mCheckbox.setText(aText);
    }
}
