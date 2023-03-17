package frc.robot.commands;

// import cwtech.util.BasicTrajectory;
// import cwtech.util.BasicTrajectory.TrajectoryCalculation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.DashboardMap;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;

public class RecalculateShootingCommand extends CommandBase {
    RobotContainer m_container;

    double m_startVelocity = 0;
    int m_ticks = 0;

    public RecalculateShootingCommand(RobotContainer container) {
        m_container = container;
        addRequirements(m_container.shooter);
    }

    @Override
    public void initialize() {
        m_startVelocity = m_container.shooter.getRequestedVelocity();
        m_ticks = 0;
    }

    @Override
    public void execute() {
        // if(m_ticks % 100 == 0) {
            var targetRpm = 0.0;
            // if(m_container.shooter.dDoManualSpeed) {
            //     targetRpm = m_container.shooter.dManualSpeed;
            // }
            // else
            {
                double distanceMeters = m_container.vision.getDistanceToHubCenterWithHeight(Vision.kHubHeightMeters);

                targetRpm = m_container.vision.shooterVelocity(distanceMeters);
            }

            // TrajectoryCalculation calculation = BasicTrajectory.calculate(SmartDashboard.getNumber(DashboardMap.kShooterEntryAngle, Shooter.kShooterEntryAngle), distanceMeters, Vision.kDifferenceMeters);
            // var targetRpm = (calculation.m_exitVelocityMetersPerSecond / (2 * Math.PI * Shooter.kWheelRadius)) * 60.0;
            // SmartDashboard.putNumber("Trajectory/RPMs", targetRpm);
            targetRpm *= SmartDashboard.getNumber(DashboardMap.kShooterMulti, Shooter.kShooterMulti);
            targetRpm += SmartDashboard.getNumber(DashboardMap.kShooterAdder, Shooter.kShooterAdder);
            if(Math.abs(targetRpm - m_startVelocity) > 500) {
                m_container.shooter.setVelocity(targetRpm);
            }
        // }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        // m_container.shooter.setVelocity(Shooter.kIdleSpeed);
    }
}

