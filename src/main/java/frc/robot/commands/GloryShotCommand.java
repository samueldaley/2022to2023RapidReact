package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;

public class GloryShotCommand extends SequentialCommandGroup {
    public GloryShotCommand(RobotContainer container) {
        addCommands(
            new SetFixedTurretHoodAndShooterCommandGroup(container, 15, 90, 4200),
            new PostShooterPrepFiringCommandGroup(container)
        );
    }
}
