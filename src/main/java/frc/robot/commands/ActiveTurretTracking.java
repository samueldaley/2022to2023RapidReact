// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class ActiveTurretTracking extends CommandBase {
  private final RobotContainer m_container;
  double m_target = 0.0;

  public ActiveTurretTracking(RobotContainer container) {
    m_container = container;
    addRequirements(m_container.turret);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_target = m_container.turret.getAngleDegrees() + m_container.vision.getTX();
    if (m_container.turret.isCloseEnoughToTarget(m_target)){
      m_container.turret.stopMovement();
    }
    else{
      m_container.turret.setDesiredAngle(m_target);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_container.turret.stopMovement();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
