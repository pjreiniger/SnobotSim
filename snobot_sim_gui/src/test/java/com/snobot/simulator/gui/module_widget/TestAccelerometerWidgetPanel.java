package com.snobot.simulator.gui.module_widget;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseGuiSimulatorTest;

import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.interfaces.Accelerometer.Range;

public class TestAccelerometerWidgetPanel extends BaseGuiSimulatorTest
{
    @Test
    public void testPanel()
    {
        DataAccessorFactory.getInstance().getI2CAccessor().createI2CSimulator(Port.kMXP.value, "ADXL345");
        ADXL345_I2C accel = new ADXL345_I2C(Port.kMXP, Range.k2G);
        AccelerometerOutputDisplay panel = new AccelerometerOutputDisplay(DataAccessorFactory.getInstance().getAccelerometerAccessor().getWrappers().keySet());

        JFrame frame = getFrame();
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);

        sleepForVisualDebugging();

        DataAccessorFactory.getInstance().getAccelerometerAccessor().getWrapper(53).setAcceleration(1);
        DataAccessorFactory.getInstance().getAccelerometerAccessor().getWrapper(54).setAcceleration(-1);
        DataAccessorFactory.getInstance().getAccelerometerAccessor().getWrapper(55).setAcceleration(0);
        panel.update();
        panel.repaint();
        sleepForVisualDebugging();

    }

}
