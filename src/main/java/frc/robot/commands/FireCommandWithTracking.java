
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;

public class FireCommandWithTracking extends SequentialCommandGroup {

  private final RobotContainer m_container;

  public FireCommandWithTracking(RobotContainer container) {
    m_container = container;

    addCommands(
      new TuneShooterAndHoodCommand(container).withInterrupt(() -> container.oi.getFireOverrided()),
      new WaitCommand(1).withInterrupt(() -> container.oi.getFireOverrided()).raceWith(new ActiveTurretTracking(container).alongWith(new RecalculateShootingCommand(container))),
      new ActiveTargetTrackingAndFire(container),
      new WaitCommand(1.0)
    );
  }

  // @Override
  // public void end(boolean interupt) {
  //   m_container.turret.setDesiredAngle(0.0);
  // }
}
