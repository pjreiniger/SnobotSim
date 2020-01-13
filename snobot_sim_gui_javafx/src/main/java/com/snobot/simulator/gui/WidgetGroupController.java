package com.snobot.simulator.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.snobot.simulator.gui.widgets.IWidgetController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class WidgetGroupController
{
    @FXML
    public VBox mMainPane;

    private final List<IWidgetController> mControllers;

    public WidgetGroupController()
    {
        mControllers = new ArrayList<>();
    }

    public void initialize(Supplier<Boolean> aSaveFunction, List<Integer> aIds, String aFxmlPath)
    {
        try
        {
            for (int id : aIds)
            {

                FXMLLoader loader = new FXMLLoader(getClass().getResource(aFxmlPath));
                Pane widgetPane = loader.load();
                mMainPane.getChildren().add(widgetPane);

                IWidgetController controller = loader.getController();
                controller.initialize(id);
                controller.setSaveAction(aSaveFunction);

                mControllers.add(controller);
            }
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void update()
    {
        for (IWidgetController controller : mControllers)
        {
            controller.update();
        }
    }

}
