package com.snobot.simulator;

import com.snobot.simulator.SimulatorPreloader.StateNotification.StateNotificationType;
import com.snobot.simulator.gui.SimulatorFrameController;
import com.snobot.simulator.wrapper_accessors.SimulatorDataAccessor.SnobotLogLevel;

import javafx.application.Application;
import javafx.application.Preloader.PreloaderNotification;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SimulatorApplication extends Application
{
    private static final String USER_CONFIG_DIR = "simulator_config/";

    private Simulator mSimulator;
    private Pane mMainPane;

    @Override
    public void init() throws Exception
    {
        boolean useSnobotSimDriverstation = !getParameters().getRaw().contains("--use_native_ds");

        notifyPreloader(new SimulatorPreloader.StateNotification(StateNotificationType.Starting));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/snobot/simulator/gui/simulator_frame.fxml"));
        mMainPane = loader.load();
        SimulatorFrameController controller = loader.getController();

        mSimulator = new Simulator(controller, SnobotLogLevel.DEBUG, USER_CONFIG_DIR, useSnobotSimDriverstation);
        mSimulator.setupSimulator(this::notifyPreloader2);

    }

    public final void notifyPreloader2(PreloaderNotification aInfo)
    {
        notifyPreloader(aInfo);
    }

    @Override
    public void start(Stage aPrimaryStage) throws Exception
    {
        aPrimaryStage.setScene(new Scene(mMainPane));
        aPrimaryStage.setTitle("SnobotSim");
        aPrimaryStage.setMinWidth(300);
        aPrimaryStage.setMinHeight(480);
        aPrimaryStage.setWidth(300);
        aPrimaryStage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());

        aPrimaryStage.show();

        mSimulator.startSimulation();

        aPrimaryStage.setOnCloseRequest(closeEvent ->
        {
            // There is no way to stop the robot thread, so kill it with fire
            System.exit(0);
        });
    }
}
