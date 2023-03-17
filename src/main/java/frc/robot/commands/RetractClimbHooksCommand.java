package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class RetractClimbHooksCommand extends CommandBase {
    private final RobotContainer m_container;

    public RetractClimbHooksCommand(RobotContainer container) {
        m_container = container;
    
        // addRequirements(m_container.climb);
    }

    @Override
    public void initialize() {
        m_container.climb.retractClimbHooks();
    }

    @Override
    public void end(boolean interupt) {
        m_container.climb.keepCurrentPosition();
    }
}
