package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.DashboardMap;
import frc.robot.RobotMap;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


public class Climb extends SubsystemBase {

    private final CANSparkMax m_rightMotor;
    private final CANSparkMax m_leftMotor;
    private final CANSparkMax m_rightRotatorMotor;
    private final CANSparkMax m_leftRotatorMotor;
    private final SparkMaxPIDController m_rightController;
    private final SparkMaxPIDController m_leftController;
    private final SparkMaxPIDController m_rightRotatorController;
    private final SparkMaxPIDController m_leftRotatorController;
    private RelativeEncoder m_rightEncoder;
    private RelativeEncoder m_leftEncoder;
    private RelativeEncoder m_rightRotatorEncoder;
    private RelativeEncoder m_leftRotatorEncoder;

    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM, maxVel, minVel, maxAcc, allowedErr;
    public double kRI, kRP;
    public static double kExtendPosition = 70, kRetractPosition = -25; // POSITION IS IN MOTOR ROTATIONS
    public static double kExtendRotatorPosition = 70, kRetractRotatorPosition = 0;
    public static double kTraverseExtendPosition = 100;

    public Climb() {
        m_rightMotor = new CANSparkMax(RobotMap.kRightClimbCANSparkMaxMotor, MotorType.kBrushless);
        m_leftMotor = new CANSparkMax(RobotMap.kLeftClimbCANSparkMaxMotor, MotorType.kBrushless);
        m_rightRotatorMotor = new CANSparkMax(RobotMap.kRightClimbRotaterCANSparkMaxMotor, MotorType.kBrushless);
        m_leftRotatorMotor = new CANSparkMax(RobotMap.kLeftClimbRotaterCANSparkMaxMotor, MotorType.kBrushless);
        m_rightController = m_rightMotor.getPIDController();
        m_leftController  = m_leftMotor.getPIDController();
        m_rightRotatorController = m_rightRotatorMotor.getPIDController();
        m_leftRotatorController = m_leftRotatorMotor.getPIDController();
        m_rightMotor.restoreFactoryDefaults();
        m_leftMotor.restoreFactoryDefaults();
        m_rightRotatorMotor.restoreFactoryDefaults();
        m_leftRotatorMotor.restoreFactoryDefaults();

        m_rightEncoder = m_rightMotor.getEncoder();
        m_leftEncoder = m_leftMotor.getEncoder();
        m_rightRotatorEncoder = m_rightRotatorMotor.getEncoder();
        m_leftRotatorEncoder = m_leftRotatorMotor.getEncoder();

        m_leftEncoder.setPosition(0);
        m_rightEncoder.setPosition(0);
        m_leftRotatorEncoder.setPosition(0);
        m_rightRotatorEncoder.setPosition(0);

        m_leftMotor.setInverted(true);

        m_leftMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        m_rightMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        m_rightRotatorMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        m_leftRotatorMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);

        m_leftMotor.setSmartCurrentLimit(60);
        m_rightMotor.setSmartCurrentLimit(60);

        kP = 5e-5;
        kI = 0;
        kD = 0;
        kIz = 0;
        kFF = 0.000156;
        kMaxOutput = 1;
        kMinOutput = -1;
        maxRPM = 4500;
        maxVel = 4500;        
        maxAcc = 4500;

        kRI = 0.00000001;
        kRP = 0.0001;

        m_rightController.setI(kI);
        m_rightController.setD(kD);
        m_rightController.setP(kP);
        m_rightController.setIZone(kIz);
        m_rightController.setFF(kFF);
        m_rightController.setOutputRange(kMinOutput, kMaxOutput);
        
        m_leftController.setI(kI);
        m_leftController.setD(kD);
        m_leftController.setP(kP);
        m_leftController.setIZone(kIz);
        m_leftController.setFF(kFF);
        m_leftController.setOutputRange(kMinOutput, kMaxOutput);

        m_rightRotatorController.setI(kRI);
        m_rightRotatorController.setD(kD);
        m_rightRotatorController.setP(kRP);
        m_rightRotatorController.setIZone(kIz);
        m_rightRotatorController.setFF(kFF);
        m_rightRotatorController.setOutputRange(kMinOutput, kMaxOutput);
        
        m_leftRotatorController.setI(kRI);
        m_leftRotatorController.setD(kD);
        m_leftRotatorController.setP(kRP);
        m_leftRotatorController.setIZone(kIz);
        m_leftRotatorController.setFF(kFF);
        m_leftRotatorController.setOutputRange(kMinOutput, kMaxOutput);
        

        int smartMotionSlot = 0;
        m_rightController.setSmartMotionMaxVelocity(maxVel, smartMotionSlot);
        m_rightController.setSmartMotionMinOutputVelocity(minVel, smartMotionSlot);
        m_rightController.setSmartMotionMaxAccel(maxAcc, smartMotionSlot);
        m_rightController.setSmartMotionAllowedClosedLoopError(allowedErr, smartMotionSlot);
        
        m_leftController.setSmartMotionMaxVelocity(maxVel, smartMotionSlot);
        m_leftController.setSmartMotionMinOutputVelocity(minVel, smartMotionSlot);
        m_leftController.setSmartMotionMaxAccel(maxAcc, smartMotionSlot);
        m_leftController.setSmartMotionAllowedClosedLoopError(allowedErr, smartMotionSlot);

        m_rightRotatorController.setSmartMotionMaxVelocity(maxVel, smartMotionSlot);
        m_rightRotatorController.setSmartMotionMinOutputVelocity(minVel, smartMotionSlot);
        m_rightRotatorController.setSmartMotionMaxAccel(maxAcc, smartMotionSlot);
        m_rightRotatorController.setSmartMotionAllowedClosedLoopError(allowedErr, smartMotionSlot);
        
        m_leftRotatorController.setSmartMotionMaxVelocity(maxVel, smartMotionSlot);
        m_leftRotatorController.setSmartMotionMinOutputVelocity(minVel, smartMotionSlot);
        m_leftRotatorController.setSmartMotionMaxAccel(maxAcc, smartMotionSlot);
        m_leftRotatorController.setSmartMotionAllowedClosedLoopError(allowedErr, smartMotionSlot);

        // SmartDashboard.putNumber("P Gain", kP);
        // SmartDashboard.putNumber("I Gain", kI);
        // SmartDashboard.putNumber("D Gain", kD);
        // SmartDashboard.putNumber("I Zone", kIz);
        // SmartDashboard.putNumber("Feed Forward", kFF);
        // SmartDashboard.putNumber("Max Output", kMaxOutput);
        // SmartDashboard.putNumber("Min Output", kMinOutput);
    
        m_leftController.setReference(0, CANSparkMax.ControlType.kPosition);
        m_rightController.setReference(0, CANSparkMax.ControlType.kPosition);
        m_leftRotatorController.setReference(0, CANSparkMax.ControlType.kPosition);
        m_rightRotatorController.setReference(0, CANSparkMax.ControlType.kPosition);
    }

    public void setPosition(double position) {
        m_leftController.setReference(position, CANSparkMax.ControlType.kPosition);
        m_rightController.setReference(position, CANSparkMax.ControlType.kPosition);
    }

    public void setRotatorPosition(double position) {
        m_leftRotatorController.setReference(position, CANSparkMax.ControlType.kPosition);
        m_rightRotatorController.setReference(position, CANSparkMax.ControlType.kPosition);
    }

    public void extendClimbHooks() {
        var target = SmartDashboard.getNumber(DashboardMap.kClimbExtendPosition, kExtendPosition);
        moveClimbHooks(target, target);
    }

    public void retractClimbHooks() {
        var target = SmartDashboard.getNumber(DashboardMap.kClimbRetractPosition, kRetractPosition);
        moveClimbHooks(target, target);
    }

    public void rotateClimbHooksBack() {
        var target = SmartDashboard.getNumber(DashboardMap.kClimbExtendRotatorPosition, kExtendRotatorPosition);
        rotateClimbHooks(target, target);
    }

    public void rotateClimbHooksForward() {
        var target = SmartDashboard.getNumber(DashboardMap.kClimbRetractRotatorPosition, kRetractRotatorPosition);
        rotateClimbHooks(target, target);
    }

    public void keepCurrentPosition() {
        moveClimbHooks(m_rightEncoder.getPosition(), m_leftEncoder.getPosition());
    }

    public void keepRotatorsCurrentPosition() {
        rotateClimbHooks(m_rightRotatorEncoder.getPosition(), m_leftRotatorEncoder.getPosition());
    }

    public void traverseExtendClimbHooks() {
        var target = SmartDashboard.getNumber(DashboardMap.kClimbTraverseExtendPosition, kTraverseExtendPosition);
        moveClimbHooks(target, target);
    }

    private void moveClimbHooks(double targetRight, double targetLeft){
        m_rightController.setReference(targetRight, CANSparkMax.ControlType.kSmartMotion);
        m_leftController.setReference(targetLeft, CANSparkMax.ControlType.kSmartMotion);
    }

    private void rotateClimbHooks(double targetRight, double targetLeft){
        m_rightRotatorController.setReference(targetRight, CANSparkMax.ControlType.kSmartMotion);
        m_leftRotatorController.setReference(targetLeft, CANSparkMax.ControlType.kSmartMotion);
    }

    public void onTeleOpPeriodic() {
        SmartDashboard.putNumber("Climb/Right Position", m_rightEncoder.getPosition());
        SmartDashboard.putNumber("Climb/Left Position", m_leftEncoder.getPosition());
        SmartDashboard.putNumber("Climb/Rotate Right Position", m_rightRotatorEncoder.getPosition());
        SmartDashboard.putNumber("Climb/Rotate Left Position", m_leftRotatorEncoder.getPosition());
    }
}
