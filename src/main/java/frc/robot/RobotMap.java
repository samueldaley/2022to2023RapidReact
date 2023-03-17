// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

public final class RobotMap {

    // CAN motor addresses
    public static final int kFrontLeftDriveCANSparkMaxMotor = 21;
    public static final int kFrontRightDriveCANSparkMaxMotor = 2;
    public static final int kBackRightDriveCANSparkMaxMotor = 3;
    public static final int kBackLeftDriveCANSparkMaxMotor = 4;
    public static final int kShooterPrimaryCANSparkMaxMotor = 6;
    public static final int kShooterFollowerCANSparkMaxMotor = 5;
    public static final int kTurretCANSparkMaxMotor = 7;
    public static final int kIntakeCANSparkMaxMotor = 8;
    public static final int kFrontLeftTurnCANSparkMaxMotor = 11;
    public static final int kFrontRightTurnCANSparkMaxMotor = 12;
    public static final int kBackRightTurnCANSparkMaxMotor = 13;
    public static final int kBackLeftTurnCANSparkMaxMotor = 14;
    public static final int kLeftClimbCANSparkMaxMotor = 15;
    public static final int kRightClimbCANSparkMaxMotor = 16;
    public static final int kCargoIndexerCANSparkMaxMotor = 18;
    public static final int kAcceleratorCANSparkMaxMotor = 19;
    public static final int kLeftClimbRotaterCANSparkMaxMotor = 30;
    public static final int kRightClimbRotaterCANSparkMaxMotor = 31;

    // PWM motor addresses
    // Servos
    public static final int kHoodServoRight = 1;
    public static final int kHoodServoLeft = 0;

    // REV Pneumatic Hub solenoid addresses
    public static final int kIntakeArmsSolenoid = 0;

    // String Pot
    public static final int kHoodStringPotAnalog = 0;

    // Digital IO addresses
    public static final int kFrontLeftTurnPulseWidthDigIO = 5;
    public static final int kFrontRightTurnPulseWidthDigIO = 8;
    public static final int kBackRightTurnPulseWidthDigIO = 7;
    public static final int kBackLeftTurnPulseWidthDigIO = 9;

    // Operator input USB ports
    public static final int kLeftJoystick = 0;
    public static final int kRightJoystick = 1;
    public static final int kButtonBox = 2;
    public static final int kXBoxController = 3;

    // Co-Pilot Buttons
    public static final int kFireButton = 1;
    public static final int kExtendClimbHooksButton = 4;
    public static final int kRetractClimbHooksButton = 5;
    public static final int kReverseIntakeButton = 10;
    public static final int kHoodDownButton = 8;
    public static final int kTestButton = 2;
    public static final int kSafeZoneShotButton = 12;
    public static final int kWallShotButton = 9;
    public static final int kGloryShotButton = 7;
    public static final int kReverseAccButton = 6;
    public static final int kHoodUpButton = 11;
    public static final int kFireOverrideButton = 3;

    // Driver Buttons
    public static final int kToggleIntakeButton = 2;

    // Zeroed values, should be in radians
    //private static final double kOffsetZeroed = Math.PI / 2;
    public static final double kZeroedFrontLeft = 3.082 + Math.toRadians(90);
    public static final double kZeroedFrontRight = 0 + Math.toRadians(90); // -3.12;
    public static final double kZeroedBackLeft = 0.520 + Math.toRadians(90);
    public static final double kZeroedBackRight = -0.230 + Math.toRadians(90);
    public static final double kZeroedTurret = 0.0;

    // Climb Buttons
    // public static final int kExtendClimbButton = 0;
    // public static final int kRetractClimbButton = 1;
    // public static final int kRotateClimbBackButton = 2;
    // public static final int kRotateClimbForwardButton = 3;
    public static final int kClimbSafetyButton = 5;
}
