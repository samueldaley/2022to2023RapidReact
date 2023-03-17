// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.DashboardMap;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Accelerator;
import frc.robot.subsystems.CargoIndexer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ColorSensor.ColorType;

public class PostShooterPrepFiringCommandGroup extends SequentialCommandGroup {

  private final RobotContainer m_container;

  public PostShooterPrepFiringCommandGroup(RobotContainer container) {
    m_container = container;

    addCommands(
      new InstantCommand(() -> {
        m_container.accelarator.setSpeed(SmartDashboard.getNumber(DashboardMap.kAcceleratorSpeed, Accelerator.kSpeed));
      }, m_container.accelarator),
      new WaitCommand(0.1),
      new InstantCommand(() -> {
        m_container.cargoIndexer.setSpeed(SmartDashboard.getNumber(DashboardMap.kCargoIndexerSpeed, CargoIndexer.kFireSpeed));
      }, m_container.cargoIndexer),
      new WaitUntilCommand(() -> m_container.colorSensor.readColor() == ColorType.None),
      new InstantCommand(() -> {
        m_container.cargoIndexer.setSpeed(0);
      }, m_container.cargoIndexer),
      new WaitCommand(0.25),
      new InstantCommand(() -> m_container.cargoIndexer.setSpeed(SmartDashboard.getNumber(DashboardMap.kCargoIndexerSpeed, CargoIndexer.kSpeed))),
      new WaitCommand(3.0)
      );
  }

  // @Override
  // public void end(boolean interupt) {
  //   m_container.shooter.setVelocity(Shooter.kIdleSpeed);
  //   m_container.accelarator.setSpeed(0);
  //   m_container.cargoIndexer.setSpeed(0);
  // }
}
