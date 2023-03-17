// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Accelerator extends SubsystemBase {
  public static final double kSpeed = 0.40;
  private final CANSparkMax m_motor;

  public Accelerator() {
    m_motor = new CANSparkMax(RobotMap.kAcceleratorCANSparkMaxMotor, CANSparkMax.MotorType.kBrushless);
    m_motor.restoreFactoryDefaults();
    // m_motor.setIdleMode(IdleMode.kCoast);
    m_motor.setIdleMode(IdleMode.kBrake);
  }

  public void setSpeed(double speed)
  {
    m_motor.set(speed);
  }
}
