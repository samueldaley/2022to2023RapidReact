package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.RobotContainer;

public class ExtendClimbHooksCommand extends InstantCommand {
    private final RobotContainer m_container;
    public ExtendClimbHooksCommand(RobotContainer container) {
        m_container = container;

        // addRequirements(m_container.climb);
    }

    @Override
    public void initialize() {
        m_container.climb.extendClimbHooks();
    }
}
