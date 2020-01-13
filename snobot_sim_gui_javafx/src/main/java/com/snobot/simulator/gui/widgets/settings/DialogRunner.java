package com.snobot.simulator.gui.widgets.settings;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;

public class DialogRunner<DialogController>
{
    private boolean mOkSelected;
    private DialogController mDialogController;
    private Alert mAlert;

    public DialogRunner(String aFxmlPath)
    {
        try
        {
            mAlert = new Alert(AlertType.NONE);
            mAlert.setTitle("Error alert");

            mAlert.getDialogPane().getButtonTypes().add(ButtonType.OK);
            mAlert.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

            FXMLLoader loader = new FXMLLoader(DialogRunner.class.getResource(aFxmlPath));
            Pane widgetPane = loader.load();
            mDialogController = loader.getController();

            mAlert.getDialogPane().setContent(widgetPane);
        }
        catch (IOException ex)
        {
            throw new RuntimeException("Could not load dialog", ex);
        }
    }

    public boolean showAndWait()
    {
        mAlert.showAndWait().ifPresent(response ->
        {
            if (response == ButtonType.OK)
            {
                mOkSelected = true;
            }
        });

        return mOkSelected;
    }

    public DialogController getController()
    {
        return mDialogController;
    }

}
