package frc.robot.commands;

// import com.pathplanner.lib.PathPlanner;
// import com.pathplanner.lib.PathPlannerTrajectory;
// import com.pathplanner.lib.commands.PPSwerveControllerCommand;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import frc.robot.RobotContainer;

public class RunPathCommand extends SequentialCommandGroup {

    RobotContainer m_container;
    Command m_pathCommand;
    Pose2d m_pose;
    int m_ticks = 0;

    public RunPathCommand(RobotContainer container, String path) {
        m_container = container;
        // PathPlannerTrajectory trajectory = PathPlanner.loadPath(path, 1, 0.5);
        // Trajectory trajectory = PathPlanner.loadPath(path, 1, 0.5);
        try {
            Trajectory trajectory = TrajectoryUtil.fromPathweaverJson(Filesystem.getDeployDirectory().toPath().resolve("pathweaver/output/" + path + ".wpilib.json"));
            SwerveControllerCommand command = new SwerveControllerCommand(trajectory,
                    () -> container.drivetrain.getPose(),
                    container.drivetrain.getKinematics(),
                    new PIDController(0.1, 0.0001, 0),
                    new PIDController(0.2, 0.0001, 0),
                    new ProfiledPIDController(0.1, 0.0005, 0, new Constraints(Math.PI, Math.PI)),
                    () -> new Rotation2d(0),
                    (SwerveModuleState[] states) -> container.drivetrain.setDesiredState(states),
                    container.drivetrain);
            m_pathCommand = command;
            m_pose = trajectory.getInitialPose();
            addCommands(
                new InstantCommand(() -> {
                    startup();
                }),
                m_pathCommand
            );
        }
        catch(Exception e) {
            DriverStation.reportError("Failed to load path", e.getStackTrace());
        }
    }

    public void startup() {
        m_container.drivetrain.resetOdometry(m_pose);
    }
}