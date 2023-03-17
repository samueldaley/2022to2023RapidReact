// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class TraverseExtendClimbHooksCommand extends CommandBase {
  private final RobotContainer m_container;

  public TraverseExtendClimbHooksCommand(RobotContainer container) {
    m_container = container;
    
    addRequirements(m_container.climb);
}

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_container.climb.traverseExtendClimbHooks();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_container.climb.keepCurrentPosition();
  }
}
