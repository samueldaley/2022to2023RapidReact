package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.REVLibError;
import com.revrobotics.SparkMaxAlternateEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.SoftLimitDirection;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Turret extends SubsystemBase {
    
    private final CANSparkMax m_turretMotor;
    private final SparkMaxAlternateEncoder m_turretEncoder;
    private final SparkMaxPIDController m_turretController;

    static public final float kMaxDegreesForwards = 45;
    static public final float kMaxDegreesBackwards = -45;

    static public final double kTurretP = 0.04;
    static public final double kTurretI = 0.00001;
    static public final double kTurretD = 0.0;
    static public final double kTurretFF = 0.01;
    static public final double kTurretIZone = 1.0;
    
    public Turret() {
        m_turretMotor = new CANSparkMax(RobotMap.kTurretCANSparkMaxMotor, CANSparkMax.MotorType.kBrushless);
        m_turretEncoder = (SparkMaxAlternateEncoder) m_turretMotor.getAlternateEncoder(SparkMaxAlternateEncoder.Type.kQuadrature, 4096);
        m_turretController = m_turretMotor.getPIDController();

        m_turretController.setFeedbackDevice(m_turretEncoder);
        m_turretController.setP(kTurretP);
        m_turretController.setI(kTurretI);
        m_turretController.setD(kTurretD);
        m_turretController.setIZone(kTurretIZone);
        m_turretController.setOutputRange(-0.8, 0.8);
        m_turretController.setFF(kTurretFF);
        m_turretMotor.enableSoftLimit(SoftLimitDirection.kForward, true);
        m_turretMotor.enableSoftLimit(SoftLimitDirection.kReverse, true);
        m_turretMotor.setSoftLimit(SoftLimitDirection.kForward, kMaxDegreesForwards);
        m_turretMotor.setSoftLimit(SoftLimitDirection.kReverse, kMaxDegreesBackwards);
    
        m_turretController.setSmartMotionMaxVelocity(3000, 0);
        m_turretController.setSmartMotionMaxAccel(2000, 0);

        m_turretEncoder.setPositionConversionFactor(360);
    }

    public void onDisabledPeriodic() {
        // m_turretController.setP(SmartDashboard.getNumber(DashboardMap.kTurretP, kTurretP));
        // m_turretController.setI(SmartDashboard.getNumber(DashboardMap.kTurretI, kTurretI));
        // m_turretController.setD(SmartDashboard.getNumber(DashboardMap.kTurretD, kTurretD));
        // m_turretController.setFF(SmartDashboard.getNumber(DashboardMap.kTurretFF, kTurretFF));
        // m_turretController.setIZone(SmartDashboard.getNumber(DashboardMap.kTurretIZon, kTurretIZone));
    }

    public void resetEncoder() {
        m_turretEncoder.setPosition(0);
    }

    public void setDesiredAngle(double degrees) {
        double newTarget = Math.min(kMaxDegreesForwards ,Math.max(degrees, kMaxDegreesBackwards));
        REVLibError error = m_turretController.setReference(newTarget, CANSparkMax.ControlType.kPosition);
        if(error != REVLibError.kOk) {
            System.err.println("Returned error on setDesiredAngle");
        }
    }

    public void stopMovement() {
        m_turretMotor.set(0.0);
    }

    public double getAngleDegrees() {
        return m_turretEncoder.getPosition();
    }

    private final double kVisionError = 2;
    public boolean isCloseEnoughToTarget(double targetAngle){
      return Math.abs(getAngleDegrees() - targetAngle) < kVisionError;
    }
}
