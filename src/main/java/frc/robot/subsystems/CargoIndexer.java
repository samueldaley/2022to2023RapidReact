// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.DashboardMap;
import frc.robot.RobotMap;

public class CargoIndexer extends SubsystemBase {
  public static final double kSpeed = 0.11;
  public static final double kFireSpeed = 0.35;
  private final CANSparkMax m_motor;
  private boolean m_extended = false;

  public CargoIndexer() {
    m_motor = new CANSparkMax(RobotMap.kCargoIndexerCANSparkMaxMotor, CANSparkMax.MotorType.kBrushless);
    m_motor.restoreFactoryDefaults();
    m_motor.setIdleMode(IdleMode.kBrake);
  }

  public void setSpeed(double speed)
  {
    m_motor.set(speed);
  }

  public void turnOnToIndex() {
    setSpeed(SmartDashboard.getNumber(DashboardMap.kCargoIndexerIntakeSpeed, kSpeed));
  }

  public void toggleIntake(boolean ballPresent) {
    if (m_extended)
    {
      m_extended = false;
      setSpeed(0);
    }
    else
    {
      m_extended = true;
      if (!ballPresent)
      {
        setSpeed(SmartDashboard.getNumber(DashboardMap.kCargoIndexerIntakeSpeed, kSpeed));
      }
    }
  }

  public void reverseIntake() {
    setSpeed(-SmartDashboard.getNumber(DashboardMap.kCargoIndexerIntakeSpeed, kSpeed));
  }

  public void restoreIntakeDirection() {
    if (m_extended)
    {
      setSpeed(SmartDashboard.getNumber(DashboardMap.kCargoIndexerIntakeSpeed, kSpeed));
    }
    else
    {
      setSpeed(0);
    }
  }

  public boolean intakeExtended() {
    return m_extended;
  }
}
