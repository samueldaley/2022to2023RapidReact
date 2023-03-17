package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DashboardMap {
    public static final String kDrivetrain = "Drivetrain";
    public static final String kDrivetrainDriveP = key("Drive P", kDrivetrain);
    public static final String kDrivetrainDriveI = key("Drive I", kDrivetrain);
    public static final String kDrivetrainDriveD = key("Drive D", kDrivetrain);
    public static final String kDrivetrainDriveFF = key("Drive FF", kDrivetrain);
    public static final String kDrivetrainTurnP = key("Turn P", kDrivetrain);
    public static final String kDrivetrainTurnI = key("Turn I", kDrivetrain);
    public static final String kDrivetrainTurnD = key("Turn D", kDrivetrain);
    public static final String kDrivetrainTurnFF = key("Turn FF", kDrivetrain);

    public static final String kOI = "OI";
    public static final String kOIXDeadband = key("X Deadband", kOI);
    public static final String kOIXExponent = key("X Exponent", kOI);
    public static final String kOIYDeadband = key("Y Deadband", kOI);
    public static final String kOIYExponent = key("Y Exponent", kOI);
    public static final String kOITurnDeadband = key("Turn Deadband", kOI);
    public static final String kOITurnExponent = key("Turn Exponent", kOI);

    public static final String kHood = "Hood";
    public static final String kHoodUseManualAngle = key("Use Specified Angle", kHood);
    public static final String kHoodManualAngle = key("Set Angle", kHood);

    public static final String kIntakeSpeed = "Intake/Speed";

    public static final String kTurret = "Turret";
    public static final String kTurretUseManualAngle = key("Use Specified Angle", kTurret);
    public static final String kTurretManualAngle = key("Set Angle", kTurret);
    public static final String kTurretP = key("P", kTurret);
    public static final String kTurretI = key("I", kTurret);
    public static final String kTurretD = key("D", kTurret);
    public static final String kTurretFF = key("FF", kTurret);
    public static final String kTurretIZon = key("IZone", kTurret);

    public static final String kShooter = "Shooter";
    public static final String kShooterUseManualSpeed = key("Use Specified Speed", kShooter);
    public static final String kShooterManualSpeed = key("Set Speed", kShooter);
    public static final String kShooterP = key("Shooter P", kShooter);
    public static final String kShooterI = key("Shooter I", kShooter);
    public static final String kShooterD = key("Shooter D", kShooter);
    public static final String kShooterFf = key("Shooter FF", kShooter);
    public static final String kShooterVelocity = key("Velocity", kShooter);
    public static final String kShooterEntryAngle = key("Entry Angle", kShooter);
    public static final String kShooterMulti = key("Multi", kShooter);
    public static final String kShooterAdder = key("Adder", kShooter);

    public static final String kTrajectory = "Trajectory";
    public static final String kTrajectoryTyOffset = key("TY Offset", kTrajectory);

    public static final String kAccelerator = "Accelerator";
    public static final String kAcceleratorSpeed = key("Speed", "Accelerator");

    public static final String kCargoIndexer = "Cargo Indexer";
    public static final String kCargoIndexerSpeed = key("Speed", kCargoIndexer);
    public static final String kCargoIndexerIntakeSpeed = key("Intake Speed", kCargoIndexer);

    public static final String kClimb = "Climb";
    public static final String kClimbExtendPosition = key("Extend Position", kClimb);
    public static final String kClimbRetractPosition = key("Retract Position", kClimb);
    public static final String kClimbExtendRotatorPosition = key("Rotator Extend Position", kClimb);
    public static final String kClimbRetractRotatorPosition = key("Rotator Retract Position", kClimb);
    public static final String kClimbTraverseExtendPosition = key("Traverse Extend Position", kClimb);

    public static final String kFieldCentric = "Field Centric";

    public static final String kDebug = "Debug Mode";

    private static String key(String key, String parent) {
        if(parent == null) {
            return key;
        }
        return parent + '/' + key;
    }

    public static boolean isDebugEnabled() {
        return SmartDashboard.getBoolean(kDebug, false);
    }
}
