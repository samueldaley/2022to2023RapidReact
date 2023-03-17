package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.DashboardMap;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Accelerator;
import frc.robot.subsystems.CargoIndexer;
import frc.robot.subsystems.Shooter;

public class FireCommand extends SequentialCommandGroup {

    private final RobotContainer m_container;

    public FireCommand(RobotContainer container) {
        m_container = container;

        addCommands(
            new TuneShooterAndHoodCommand(container),
            new WaitCommand(1),
            new InstantCommand(() -> {
              m_container.accelarator.setSpeed(SmartDashboard.getNumber(DashboardMap.kAcceleratorSpeed, Accelerator.kSpeed));
              m_container.cargoIndexer.setSpeed(SmartDashboard.getNumber(DashboardMap.kCargoIndexerSpeed, CargoIndexer.kFireSpeed));
            }, m_container.accelarator, m_container.cargoIndexer),
            new WaitCommand(3.0)
        );
    }

    // @Override
    // public void end(boolean interupt) {
    //   m_container.shooter.setVelocity(Shooter.kIdleSpeed);
    //   m_container.accelarator.setSpeed(0);
    //   m_container.cargoIndexer.setSpeed(0);
    // }
  }
