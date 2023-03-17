package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;

public class ThreeBallAutonomousCommandGroup extends SequentialCommandGroup {
    public ThreeBallAutonomousCommandGroup(RobotContainer container) {
        addCommands(
            new IntakeToggleCommand(container),
            new RunPathCommand(container, "3ballblue1"),
            new FireCommandWithTracking(container),
            new RunPathCommand(container, "3ball2blue2")
        );
    }
}
