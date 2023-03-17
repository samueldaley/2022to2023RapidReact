
package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.SparkMaxRelativeEncoder;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;

import cwtech.telemetry.Level;
import cwtech.telemetry.Telemetry;
import cwtech.telemetry.Updateable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.DashboardMap;
import frc.robot.RobotMap;

@Telemetry
public class Shooter extends SubsystemBase {
    public static final double kShooterFf = 0.00018;
    public static final double kShooterKp = 0.000205;
    public static final double kShooterKi = 0.00000001;
    public static final double kShooterKd = 0.0;
    public static final double kShooterEntryAngle = -70;
    public static final double kShooterMulti = 1;//0.985;
    public static final double kShooterAdder = -500.0;

    private final CANSparkMax m_mainMotor;
    private final CANSparkMax m_followerMotor;
    private final SparkMaxPIDController m_mainMotorController;
    private final SparkMaxRelativeEncoder m_mainMotorEncoder;
    static public final double kWheelRadius = 2.5 * 0.0254;
    public static final double kIdleSpeed = 100;
    private double m_requestedVelocity = 0;

    @Updateable(key = "P", level = Level.Competition, defaultNumber = kShooterKp, whenDisabled = true)
    void m_setP(double p) {
        m_mainMotorController.setP(p);
    }

    @Updateable(key = "I", level = Level.Competition, defaultNumber = kShooterKi, whenDisabled = true)
    void m_setI(double i) {
        m_mainMotorController.setI(i);
    }

    @Updateable(key = "D", level = Level.Competition, defaultNumber = kShooterKd, whenDisabled = true)
    void m_setD(double d) {
        m_mainMotorController.setD(d);
    }

    @Updateable(key = "FF", level = Level.Competition, defaultNumber = kShooterFf, whenDisabled = true)
    void m_setFF(double ff) {
        m_mainMotorController.setFF(ff);
    }

    @Updateable(key = "Do Manual Speed")
    public boolean dDoManualSpeed = false;

    @Updateable(key = "Manual Speed")
    public double dManualSpeed = 0.0;

    public Shooter() {
        m_mainMotor = new CANSparkMax(RobotMap.kShooterPrimaryCANSparkMaxMotor, CANSparkMax.MotorType.kBrushless);
        m_followerMotor = new CANSparkMax(RobotMap.kShooterFollowerCANSparkMaxMotor, CANSparkMax.MotorType.kBrushless);
        m_mainMotor.restoreFactoryDefaults();
        m_mainMotor.setIdleMode(IdleMode.kCoast);
        m_mainMotorController = m_mainMotor.getPIDController();
        m_mainMotorEncoder = (SparkMaxRelativeEncoder) m_mainMotor.getEncoder();
         
        m_mainMotorController.setFF(kShooterFf);
        m_mainMotorController.setP(kShooterKp);
        m_mainMotorController.setI(kShooterKi);
        m_mainMotorController.setD(kShooterKd);
    
        m_followerMotor.follow(m_mainMotor, true);
    
        m_mainMotorController.setReference(kIdleSpeed, ControlType.kVelocity);
    }

    public void onDisabledPeriodic() {
        m_mainMotorController.setFF(SmartDashboard.getNumber(DashboardMap.kShooterFf, kShooterFf));
        m_mainMotorController.setP(SmartDashboard.getNumber(DashboardMap.kShooterP, kShooterKp));
        m_mainMotorController.setI(SmartDashboard.getNumber(DashboardMap.kShooterI, kShooterKi));
        m_mainMotorController.setD(SmartDashboard.getNumber(DashboardMap.kShooterD, kShooterKd));
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber(DashboardMap.kShooterVelocity, getVelocity());
    }

    /** @param speed Value between 0 and 4500 */
    public void setVelocity(double rpm) {
        rpm = Math.min(4500, rpm);
        m_requestedVelocity = rpm;
        m_mainMotorController.setReference(rpm, CANSparkMax.ControlType.kVelocity);
    }

    public double getRequestedVelocity() {
        return m_requestedVelocity;
    }

    public double getVelocity() {
        return m_mainMotorEncoder.getVelocity();
    }
}

