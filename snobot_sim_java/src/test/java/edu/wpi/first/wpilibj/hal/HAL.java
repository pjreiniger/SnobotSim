package edu.wpi.first.wpilibj.hal;

public class HAL {

    public static void report(int resource, int instanceNumber) {
        report(resource, instanceNumber, 0, "");
    }

    public static void report(int resource, int instanceNumber, int context) {
        report(resource, instanceNumber, context, "");
    }
    public static int report(int resource, int instanceNumber, int context, String feature)
    {
        return edu.wpi.first.hal.HAL.report(resource, instanceNumber, context, feature);
    }
}
