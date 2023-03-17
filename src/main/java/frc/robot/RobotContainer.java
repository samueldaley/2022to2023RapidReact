// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.function.BooleanSupplier;

import cwtech.telemetry.Manager;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.TrapezoidProfileCommand;
import frc.robot.commands.AutoComeForwardCommand;
import frc.robot.commands.AutoCommand;
import frc.robot.commands.DriveOnlyAutoCommand;
import frc.robot.commands.PositionBallCommand;
import frc.robot.commands.TeleopDriveCommand;
import frc.robot.commands.ThreeBallAutonomousCommandGroup;
import frc.robot.subsystems.Accelerator;
import frc.robot.subsystems.CargoIndexer;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.ColorSensor;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;

public class RobotContainer {
    public final Climb climb = new Climb();
    public final Drivetrain drivetrain = new Drivetrain();
    public final Hood hood = new Hood();
    public final Shooter shooter = new Shooter();
    public final Turret turret = new Turret();
    public final Vision vision = new Vision();
    public final Intake intake = new Intake();
    public final ColorSensor colorSensor = new ColorSensor();
    public final Accelerator accelarator = new Accelerator();
    public final CargoIndexer cargoIndexer = new CargoIndexer();
    public final OI oi = new OI(this);

    private final AutoCommand m_autoCommand = new AutoCommand(this);
    private final AutoComeForwardCommand m_autoComeForwardCommand = new AutoComeForwardCommand(this);
    private final DriveOnlyAutoCommand m_driveOnlyAutoCommand = new DriveOnlyAutoCommand(this);
    private final Command m_autoPathCommand = new ThreeBallAutonomousCommandGroup(this);
    public final SendableChooser<Command> m_autoChooser = new SendableChooser<Command>();

    private final Manager m_telemetryManager = new Manager();

    public RobotContainer(BooleanSupplier isInTeleopEnabled) {
        drivetrain.setDefaultCommand(new TeleopDriveCommand(this, true, isInTeleopEnabled));
        cargoIndexer.setDefaultCommand(new PositionBallCommand(this));
        m_autoChooser.addOption("2 Ball Away", m_autoCommand);
        m_autoChooser.setDefaultOption("2 Ball Close", m_autoComeForwardCommand);
        m_autoChooser.addOption("Drive", m_driveOnlyAutoCommand);
        m_autoChooser.addOption("Path", m_autoPathCommand);
        SmartDashboard.putData("Auto", m_autoChooser);

        m_telemetryManager.register(drivetrain);
        m_telemetryManager.register(shooter);
        m_telemetryManager.register(hood);
    }

    public void init() {
        m_telemetryManager.inital();
    }

    public void periodic() {
        m_telemetryManager.run();
    }
}
