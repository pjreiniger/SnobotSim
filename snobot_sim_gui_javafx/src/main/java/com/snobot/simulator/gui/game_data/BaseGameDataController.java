package com.snobot.simulator.gui.game_data;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.simulator.wrapper_accessors.DriverStationDataAccessor.MatchType;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public abstract class BaseGameDataController
{
    @FXML
    protected TextField mMatchNumber;

    @FXML
    protected ComboBox<MatchType> mMatchType;

    @FXML
    protected TextField mEventName;

    @FXML
    public void initialize()
    {
        mMatchType.getItems().addAll(MatchType.values());
        mMatchType.getSelectionModel().select(MatchType.Practice);
    }

    @FXML
    protected void handleUpdate()
    {
        int matchNumber = 1;

        try
        {
            matchNumber = Integer.parseInt(mMatchNumber.getText());
        }
        catch (NumberFormatException ex)
        {
            LogManager.getLogger().log(Level.ERROR, "Could not parse match number", ex);
        }

        System.out.println("Setting game data");

        DataAccessorFactory.getInstance().getDriverStationAccessor().setMatchInfo(mEventName.getText(),
                mMatchType.getSelectionModel().getSelectedItem(), matchNumber, 0, getGameData());
    }

    protected abstract String getGameData();
}
