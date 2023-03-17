package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

/** Snaps Turret to Target */
public class SnapTurretToTarget extends CommandBase {
    private final RobotContainer m_container;
    double m_target = 0.0;

    public SnapTurretToTarget(RobotContainer container) {
        m_container = container;

        addRequirements(m_container.turret);
    }

    @Override
    public void initialize() {
        m_target = m_container.turret.getAngleDegrees() + m_container.vision.getTX();
        m_container.turret.setDesiredAngle(m_target);
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return m_container.turret.isCloseEnoughToTarget(m_target);
    }
    
    @Override
    public void end(boolean interupt) {
        m_container.turret.stopMovement();
    }
}
