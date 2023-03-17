package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ColorSensor;

public class IntakeToggleCommand extends InstantCommand {

    private final RobotContainer m_container;

    public IntakeToggleCommand(RobotContainer container) {
        m_container = container;
    
        addRequirements(m_container.intake);
        addRequirements(m_container.cargoIndexer);
    }

    @Override
    public void initialize() {
        m_container.intake.toggleIntake();
        // m_container.cargoIndexer.toggleIntake(false);
        m_container.cargoIndexer.toggleIntake(m_container.colorSensor.readColor() != ColorSensor.ColorType.None);
    }
}
