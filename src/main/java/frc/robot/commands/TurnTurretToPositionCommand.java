// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class TurnTurretToPositionCommand extends CommandBase {
    RobotContainer m_container;
    double m_target = 0.0;
    /** Creates a new TurnTurretToPositionCommand. */
  public TurnTurretToPositionCommand(RobotContainer container, double target) {
      m_container = container;
      m_target = target;
      addRequirements(m_container.turret);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // if(SmartDashboard.getBoolean(DashboardMap.kTurretUseManualAngle, false)) {
    //   m_target = SmartDashboard.getNumber(DashboardMap.kTurretManualAngle, 0.0);
    // }
    m_container.turret.setDesiredAngle(m_target);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_container.turret.stopMovement();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
      return m_container.turret.isCloseEnoughToTarget(m_target);
  }
}
