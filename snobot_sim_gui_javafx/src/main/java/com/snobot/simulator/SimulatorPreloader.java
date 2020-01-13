package com.snobot.simulator;

import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SimulatorPreloader extends Preloader
{
    private Stage mPreloaderStage;
    private SimulatorPreloaderController mController;

    @Override
    public void start(Stage aStage) throws Exception
    {
        mPreloaderStage = aStage;

        FXMLLoader loader = new FXMLLoader(SimulatorPreloader.class.getResource("preloader.fxml"));

        Pane preloaderPane = loader.load();
        mController = loader.getController();

        Scene scene = new Scene(preloaderPane);

        aStage.setScene(scene);
        aStage.initStyle(StageStyle.UNDECORATED);
        aStage.show();
    }

    @Override
    public void handleApplicationNotification(PreloaderNotification aInfo)
    {
        StateNotification notification = (StateNotification) aInfo;
        System.out.println("Getting notification...." + aInfo);
        mController.setStateText(notification.getState());
        mController.setProgress(notification.getProgress());
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification aInfo)
    {
        if (aInfo.getType() == StateChangeNotification.Type.BEFORE_START)
        {
            mPreloaderStage.close();
        }
    }

    public static final class StateNotification implements PreloaderNotification
    {
        public static enum StateNotificationType
        {
            Starting,
            LoadingSimulatorProperties,
            CreatingRobot,
            StartingRobotThread, 
            WaitingForProgramToStart, 
            Finished;
        }

        private final StateNotificationType mState;

        public StateNotification(StateNotificationType aState)
        {
            mState = aState;
        }

        public double getProgress()
        {
            return mState.ordinal() / (StateNotificationType.values().length - 1.0);
        }

        public String getState()
        {
            return mState.toString();
        }

        @Override
        public String toString()
        {
            return mState.toString();
        }
    }
}
