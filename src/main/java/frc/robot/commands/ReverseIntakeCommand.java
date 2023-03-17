package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class ReverseIntakeCommand extends CommandBase {
    private final RobotContainer m_container;

    public ReverseIntakeCommand(RobotContainer container) {
        m_container = container;
    
        addRequirements(m_container.intake, m_container.cargoIndexer);
    }

    @Override
    public void initialize() {
        m_container.intake.reverseIntake();
        m_container.cargoIndexer.reverseIntake();
    }

    @Override
    public void end(boolean interupt) {
        m_container.intake.restoreIntakeDirection();
        m_container.cargoIndexer.restoreIntakeDirection();
    }
}
