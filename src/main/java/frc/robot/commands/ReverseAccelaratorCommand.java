package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.DashboardMap;
import frc.robot.RobotContainer;

public class ReverseAccelaratorCommand extends CommandBase {
    private final RobotContainer m_container;
    public ReverseAccelaratorCommand(RobotContainer container) {
        m_container = container;
    
        addRequirements(m_container.cargoIndexer, m_container.accelarator);
    }

    @Override
    public void initialize() {
        m_container.accelarator.setSpeed(-SmartDashboard.getNumber(DashboardMap.kAcceleratorSpeed, 0.0));
        m_container.cargoIndexer.setSpeed(-SmartDashboard.getNumber(DashboardMap.kCargoIndexerSpeed, 0.0));
    }

    @Override 
    public void end(boolean i) {
        m_container.accelarator.setSpeed(0);
        m_container.cargoIndexer.setSpeed(0);
    }
}
