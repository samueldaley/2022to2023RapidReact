package frc.robot.subsystems;

import cwtech.telemetry.Observable;
import cwtech.telemetry.Telemetry;
import cwtech.telemetry.Updateable;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.PWM;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

@Telemetry
public class Hood extends SubsystemBase implements AutoCloseable {
    public static final int kMiddle = 994;
    public static final double kMinEncoderOffset = 0.095;
    public static final double kMaxEncoderOffset = 0.68;
    public static final double kMinDegrees = (90 - 33.4);
    public static final double kMaxDegress = (90 - 60);
    private static final double m_EncoderPerDegreeSlope = (kMaxEncoderOffset - kMinEncoderOffset) / (kMaxDegress - kMinDegrees);
    private static final double m_EncoderPerDegreeOffset = kMinEncoderOffset - kMinDegrees * m_EncoderPerDegreeSlope;

    private final PWM m_left;
    private final PWM m_right;

    private final AnalogPotentiometer m_potentiometer;

    @Updateable(key = "Do Manual Angle")
    public boolean pDoManualAngle = false;

    @Updateable(key = "Manual Angle")
    public double pManualAngle = 1.0;

    public Hood() {
        m_left = new PWM(RobotMap.kHoodServoLeft);
        m_right = new PWM(RobotMap.kHoodServoRight);

        m_potentiometer = new AnalogPotentiometer(RobotMap.kHoodStringPotAnalog);

        m_left.setPeriodMultiplier(PWM.PeriodMultiplier.k4X);
        m_right.setPeriodMultiplier(PWM.PeriodMultiplier.k4X);

        setSpeed(0.0);
    }

    public double getMaxEncoder() {
        return kMaxEncoderOffset;
    }

    public double getMinEncoder() {
        return kMinEncoderOffset;
    }

    public double convertToEncoderPositionFromDegrees(double degrees) {
        return degrees * m_EncoderPerDegreeSlope + m_EncoderPerDegreeOffset;
    }

    public double convertToDegreesFromEncoderPosition(double position) {
        return (position - m_EncoderPerDegreeOffset) / m_EncoderPerDegreeSlope;
    }

    /** Sets the speed of the Hood 
     * @param speed a value between -1.0 and 1.0
    */
    public void setSpeed(double speed) {
        speed = speed * 100;
        int rawSpeed = (int) speed;
        m_left.setRaw(kMiddle + rawSpeed);
        m_right.setRaw(kMiddle - rawSpeed);
    }

    // returns 0.0 to 1.0
    @Observable(key = "String Pot Position")
    public double getPosition() {
        return m_potentiometer.get();
    }

    @Observable(key = "Position(Degrees)")
    double getPositionDegrees() {
        return convertToDegreesFromEncoderPosition(getPosition());
    }

    // @Override
    // public void periodic() {
        // SmartDashboard.putNumber("Hood/Position", getPosition());
    //     SmartDashboard.putNumber("Hood/Position(Degrees)", convertToDegreesFromEncoderPosition(getPosition()));

    //     // setSpeed(m_controller.calculate(getPosition()));        
    // }

    @Override
    public void close() throws Exception{
        m_left.close();
        m_right.close();
        m_potentiometer.close();
    }
}
