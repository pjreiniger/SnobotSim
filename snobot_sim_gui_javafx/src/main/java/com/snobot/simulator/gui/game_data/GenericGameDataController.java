package com.snobot.simulator.gui.game_data;


import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class GenericGameDataController extends BaseGameDataController
{
    @FXML
    protected TextField mGameData;

    @Override
    protected String getGameData()
    {
        return mGameData.getText();
    }
}
