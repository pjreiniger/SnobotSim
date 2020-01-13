package com.snobot.simulator.gui.widgets.settings;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class BasicSettingsDialog
{
    @FXML
    private TextField mNameField;

    public BasicSettingsDialog()
    {
    }

    public void setName(String aName)
    {
        mNameField.setText(aName);
    }

    public String getDisplayName()
    {
        return mNameField.getText();
    }
}
