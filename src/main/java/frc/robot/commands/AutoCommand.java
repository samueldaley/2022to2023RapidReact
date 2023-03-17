package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;

public class AutoCommand extends SequentialCommandGroup {
    public AutoCommand(RobotContainer container) {
        addCommands(
            new WaitCommand(1),
            new InstantCommand(() -> {
                container.drivetrain.drive(-1.35, 0, 0, false);
            }, container.drivetrain),
            new IntakeToggleCommand(container),
            new WaitCommand(1.25),
            new InstantCommand(() -> {
                container.drivetrain.drive(0, 0, 0, false);
            }, container.drivetrain),
            new FireCommandWithTracking(container),
            // new FireCommand(container),
            new SetHoodAngleCommand(container, 1)
        );
    }
}
