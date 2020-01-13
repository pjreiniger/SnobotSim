package com.snobot.simulator;

import com.snobot.simulator.gui.motor_graphs.MotorCurveDisplayController;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public final class TestMain
{
    private TestMain()
    {

    }

    public static class PseudoMain extends Application
    {
        @Override
        public void start(Stage aPrimaryStage) throws Exception
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/snobot/simulator/gui/motor_graphs/motor_curve_display.fxml"));
            Pane root = loader.load();
            MotorCurveDisplayController controller = loader.getController();


            Scene scene = new Scene(root);
            aPrimaryStage.setScene(scene);
            aPrimaryStage.show();
            controller.setCurveParams("CIM", 12, 5330, 131, 2.7, 2.410);

            aPrimaryStage.setOnHidden(new EventHandler<WindowEvent>()
            {

                @Override
                public void handle(WindowEvent aEvent)
                {
                    Platform.runLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            System.exit(0);
                        }
                    });
                }
            });
        }
    }

    public static void main(String[] aArgs)
    {
        DefaultDataAccessorFactory.initalize();

        // JavaFX 11+ uses GTK3 by default, and has problems on some display
        // servers
        // This flag forces JavaFX to use GTK2
        // System.setProperty("jdk.gtk.version", "2");
        Application.launch(PseudoMain.class, aArgs);
    }
}
