// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.Accelerator;
import frc.robot.subsystems.CargoIndexer;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;

// Do code sa java ateema, do chowbaso

public class Robot extends TimedRobot {
    private RobotContainer m_robotContainer = new RobotContainer(() -> {return isTeleopEnabled();});
    private int m_initTicks = 1;

    @Override
    public void robotInit() {
        LiveWindow.disableAllTelemetry();
        SmartDashboard.putString("MESSAGE", "started");
        // SmartDashboard.putData("CS", CommandScheduler.getInstance());

        // SmartDashboard.putNumber(DashboardMap.kDrivetrainDriveP, SwerveModule.kDriveKp);
        // SmartDashboard.putNumber(DashboardMap.kDrivetrainDriveI, SwerveModule.kDriveKi);
        // SmartDashboard.putNumber(DashboardMap.kDrivetrainDriveD, SwerveModule.kDriveKd);
        // SmartDashboard.putNumber(DashboardMap.kDrivetrainDriveFF, SwerveModule.kDriveFf);
        // SmartDashboard.putNumber(DashboardMap.kDrivetrainTurnP, SwerveModule.kTurnKp);
        // SmartDashboard.putNumber(DashboardMap.kDrivetrainTurnI, SwerveModule.kTurnKi);
        // SmartDashboard.putNumber(DashboardMap.kDrivetrainTurnD, SwerveModule.kTurnKd);
        // SmartDashboard.putNumber(DashboardMap.kDrivetrainTurnFF, SwerveModule.kTurnFf);
        // SmartDashboard.putNumber(DashboardMap.kOIXDeadband, OI.kDriverXdeadband);
        // SmartDashboard.putNumber(DashboardMap.kOIXExponent, OI.kDriverXexponent);
        // SmartDashboard.putNumber(DashboardMap.kOIYDeadband, OI.kDriverYdeadband);
        // SmartDashboard.putNumber(DashboardMap.kOIYExponent, OI.kDriverYexponent);
        // SmartDashboard.putNumber(DashboardMap.kOITurnDeadband, OI.kDriverRotationDeadband);
        // SmartDashboard.putNumber(DashboardMap.kOITurnExponent, OI.kDriverRotationExponent);

        SmartDashboard.putNumber(DashboardMap.kIntakeSpeed, Intake.kIntakeSpeed);

        SmartDashboard.putNumber(DashboardMap.kTrajectoryTyOffset, Vision.kCameraTYOffset);

        // SmartDashboard.putBoolean(DashboardMap.kHoodUseManualAngle, false);
        // SmartDashboard.putNumber(DashboardMap.kHoodManualAngle, 0.5);
        // SmartDashboard.putBoolean(DashboardMap.kShooterUseManualSpeed, false);
        // SmartDashboard.putNumber(DashboardMap.kShooterManualSpeed, 1500);

        // SmartDashboard.putBoolean(DashboardMap.kTurretUseManualAngle, false);
        // SmartDashboard.putNumber(DashboardMap.kTurretManualAngle, 0.0);
  
        // SmartDashboard.putNumber(DashboardMap.kTurretP, Turret.kTurretP);
        // SmartDashboard.putNumber(DashboardMap.kTurretI, Turret.kTurretI);
        // SmartDashboard.putNumber(DashboardMap.kTurretD, Turret.kTurretD);
        // SmartDashboard.putNumber(DashboardMap.kTurretFF, Turret.kTurretFF);
        // SmartDashboard.putNumber(DashboardMap.kTurretIZon, Turret.kTurretIZone);

        SmartDashboard.putNumber(DashboardMap.kShooterFf, Shooter.kShooterFf);
        SmartDashboard.putNumber(DashboardMap.kShooterP, Shooter.kShooterKp);
        SmartDashboard.putNumber(DashboardMap.kShooterI, Shooter.kShooterKi);
        SmartDashboard.putNumber(DashboardMap.kShooterD, Shooter.kShooterKd);
        SmartDashboard.putNumber(DashboardMap.kShooterMulti, Shooter.kShooterMulti);
        SmartDashboard.putNumber(DashboardMap.kShooterAdder, Shooter.kShooterAdder);
        SmartDashboard.putNumber(DashboardMap.kShooterEntryAngle, Shooter.kShooterEntryAngle);

        SmartDashboard.putNumber(DashboardMap.kCargoIndexerIntakeSpeed, CargoIndexer.kSpeed);
        SmartDashboard.putNumber(DashboardMap.kCargoIndexerSpeed, CargoIndexer.kFireSpeed);

        SmartDashboard.putNumber(DashboardMap.kAcceleratorSpeed, Accelerator.kSpeed);

        // SmartDashboard.putNumber(DashboardMap.kClimbExtendPosition, Climb.kExtendPosition);
        // SmartDashboard.putNumber(DashboardMap.kClimbRetractPosition, Climb.kRetractPosition);
        // SmartDashboard.putNumber(DashboardMap.kClimbRetractRotatorPosition, Climb.kRetractRotatorPosition);
        // SmartDashboard.putNumber(DashboardMap.kClimbExtendRotatorPosition, Climb.kExtendRotatorPosition);
        // SmartDashboard.putNumber(DashboardMap.kClimbTraverseExtendPosition, Climb.kTraverseExtendPosition);

        SmartDashboard.putBoolean(DashboardMap.kFieldCentric, true);
        m_robotContainer.init();
    }

    @Override
    public void robotPeriodic() {
        if (m_initTicks > 0)
        {
            m_initTicks++;
            if (m_initTicks == 100)
            {
                m_robotContainer.drivetrain.setInitalPositions();
                m_robotContainer.drivetrain.resetNavX();
                m_robotContainer.turret.resetEncoder();
                m_robotContainer.vision.setLedMode(2);
                SmartDashboard.putString("MESSAGE", "initialized");
            }
            else if(m_initTicks == 500) {
                m_initTicks = -1;
                m_robotContainer.vision.setLedMode(0);
                // m_robotContainer.vision.startingUp = false;
            }
        }

        CommandScheduler.getInstance().run();
        m_robotContainer.periodic();
    }

    @Override
    public void disabledInit() {
        m_robotContainer.drivetrain.onDisabledInit();
        m_robotContainer.oi.onDisabledInit();
        m_robotContainer.intake.onDisabledInit();
        // m_robotContainer.vision.setLedMode(1);
    }

    @Override
    public void disabledPeriodic() {
        m_robotContainer.shooter.onDisabledPeriodic();
        m_robotContainer.turret.onDisabledPeriodic();
    }

    @Override
    public void autonomousInit() {
        m_robotContainer.vision.setLedMode(0);
        m_robotContainer.m_autoChooser.getSelected().schedule();
    }

    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopInit() {
        // m_robotContainer.vision.setLedMode(0);
    }

    @Override
    public void teleopPeriodic() {
        // m_robotContainer.climb.onTeleOpPeriodic();
    }

    @Override
    public void testInit() {
    }

    @Override
    public void testPeriodic() { 
    }
}
