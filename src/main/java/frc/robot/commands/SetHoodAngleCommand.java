package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Vision;

public class SetHoodAngleCommand extends CommandBase {
    private final RobotContainer m_container;
    private double m_hoodPositionDegrees = 0.0;
    private double m_hoodPosition = 0.0;
    private boolean m_backwards;
    private final double kSpeed = 1.0;

    public SetHoodAngleCommand(RobotContainer container) { 
        m_container = container;
        m_backwards = false;

        addRequirements(m_container.hood);
    }

    public SetHoodAngleCommand(RobotContainer container, double positionInDegrees) {
        m_container = container;
        m_hoodPositionDegrees = positionInDegrees;
        m_backwards = false;

        addRequirements(m_container.hood);
    }

    @Override
    public void initialize() {
        if (m_hoodPositionDegrees <= 0)
        {
            // if(SmartDashboard.getBoolean(DashboardMap.kHoodUseManualAngle, false)) {
            //     m_hoodPositionDegrees = SmartDashboard.getNumber(DashboardMap.kHoodManualAngle, Hood.kMinDegrees);
            //     SmartDashboard.putNumber("Hood Target Debug - Raw Degrees", m_hoodPositionDegrees);
            // }
            // if(m_container.hood.pDoManualAngle) {
            //     m_hoodPositionDegrees = m_container.hood.pManualAngle;
            // }
            // else
            {
                double distanceMeters = m_container.vision.getDistanceToHubCenterWithHeight(Vision.kHubHeightMeters);

                m_hoodPositionDegrees = m_container.vision.shooterAngleDegrees(distanceMeters);

                // TrajectoryCalculation calculation = BasicTrajectory.calculate(SmartDashboard.getNumber(DashboardMap.kShooterEntryAngle, Shooter.kShooterEntryAngle), distanceMeters, Vision.kDifferenceMeters);
                // m_hoodPositionDegrees = calculation.m_shootingAngleDegrees;
                // SmartDashboard.putNumber("Hood Target Angle From Vision", m_hoodPositionDegrees);
            }
        }
        m_hoodPosition = m_container.hood.convertToEncoderPositionFromDegrees(m_hoodPositionDegrees);

        // SmartDashboard.putNumber("Hood Target Debug", m_hoodPosition);

        m_hoodPosition = Math.max(m_hoodPosition, m_container.hood.getMinEncoder());
        m_hoodPosition = Math.min(m_hoodPosition, m_container.hood.getMaxEncoder());

        // SmartDashboard.putNumber("Hood Target Debug Limited", m_hoodPosition);
        double hoodPosition = m_container.hood.getPosition();
    
        // SmartDashboard.putNumber("Hood Position Debug", hoodPosition);

        if(hoodPosition < m_hoodPosition) {
            m_container.hood.setSpeed(kSpeed);
            m_backwards = false;
        } 
        else {
            m_container.hood.setSpeed(-kSpeed);
            m_backwards = true;
        }
    }

    @Override
    public boolean isFinished() {
        double currentHoodPosition = m_container.hood.getPosition();

        // Checks to see if at or pass requested hood position and if so stop
        return m_backwards ? currentHoodPosition <= m_hoodPosition : currentHoodPosition >= m_hoodPosition;
    }

    @Override
    public void end(boolean interrupted) {
        m_container.hood.setSpeed(0.0);
    }
}
