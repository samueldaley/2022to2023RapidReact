package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.DashboardMap;
import frc.robot.RobotMap;

public class Intake extends SubsystemBase implements AutoCloseable {
    public static final double kIntakeSpeed = 0.65;
    private final Solenoid m_arms;
    private final CANSparkMax m_motor;

    private double m_intakeSpeed = kIntakeSpeed;

    public Intake() {
        m_arms = new Solenoid(PneumaticsModuleType.REVPH, RobotMap.kIntakeArmsSolenoid);
        m_motor = new CANSparkMax(RobotMap.kIntakeCANSparkMaxMotor, CANSparkMax.MotorType.kBrushless);
    }

    public void toggleIntake() {
        if (m_arms.get() == true) {
            m_arms.set(false);
            m_motor.set(0);
        } else {
            m_arms.set(true);
            m_motor.set(m_intakeSpeed);
        }
    }

    public void reverseIntake() {
        m_motor.set(-m_intakeSpeed);
    }

    public void restoreIntakeDirection() {
        if(m_arms.get() == true) {
            m_motor.set(m_intakeSpeed);
        }
        else{
            m_motor.set(0);
        }
    }

    @Override
    public void close() {
        m_arms.close();
        m_motor.close();
    }

    public void onDisabledInit() {
        m_intakeSpeed = SmartDashboard.getNumber(DashboardMap.kIntakeSpeed, kIntakeSpeed);
    }

    public boolean isExtended() {
        return m_arms.get();
    }
}
