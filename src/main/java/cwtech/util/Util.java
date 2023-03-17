// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package cwtech.util;

/** Add your docs here. */
public class Util {
    public static boolean dcompare(double left, double right) {
        final double kThreshold = 0.001;
        return Math.abs(left - right) < kThreshold;
    }
    public static boolean dcompareMine(double left, double right, double threshold) {
        return Math.abs(left - right) < threshold;
    }
}
