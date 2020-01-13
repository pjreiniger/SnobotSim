package com.snobot.simulator.gui.game_data;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class PowerUpGameDataController extends BaseGameDataController
{
    @FXML
    protected ComboBox<String> mGameData;

    @Override
    @FXML
    public void initialize()
    {
        mGameData.getSelectionModel().select("LLL");
        super.initialize();
    }

    @Override
    protected String getGameData()
    {
        return mGameData.getSelectionModel().getSelectedItem();
    }
}
