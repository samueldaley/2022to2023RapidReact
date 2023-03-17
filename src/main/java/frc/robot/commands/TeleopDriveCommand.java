package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.DashboardMap;
import frc.robot.RobotContainer;

// Java do code sa ateema

public class TeleopDriveCommand extends CommandBase {
    private final RobotContainer m_container;
    private final BooleanSupplier m_isInTeleopEnabled;

    public TeleopDriveCommand(RobotContainer container, boolean fieldRelative, BooleanSupplier isInTeleopEnabled) {
        m_container = container;
        m_isInTeleopEnabled = isInTeleopEnabled;

        addRequirements(m_container.drivetrain);
    }

    @Override
    public void execute() {
        if (m_isInTeleopEnabled.getAsBoolean() == false) return;

        m_container.drivetrain.drive(
            m_container.oi.getDriveSpeedX(),
            m_container.oi.getDriveSpeedY(),
            m_container.oi.getDriveSpeedRotation(),
            SmartDashboard.getBoolean(DashboardMap.kFieldCentric, true));
    }

    @Override
    public void end(boolean interupted) {
        m_container.drivetrain.drive(0, 0, 0, false);
    }
}
