package frc.robot.subsystems;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public final class ColorSensor extends SubsystemBase {

    private final ColorSensorV3 m_colorSensor;
    private final ColorMatch m_colorMatcher;
    private final Color m_blueTarget = new Color(0.19, 0.43, 0.38);
    private final Color m_redTarget = new Color(0.44, 0.39, 0.17);

    
    public ColorSensor() {
        m_colorSensor = new ColorSensorV3(Port.kOnboard);
        m_colorMatcher = new ColorMatch(); 

        m_colorMatcher.addColorMatch(m_blueTarget);
        m_colorMatcher.addColorMatch(m_redTarget);
    }

    public enum ColorType {
        Red,
        Blue,
        None,
    }

    public ColorType readColor() {
        Color detectedColor = m_colorSensor.getColor();
        ColorMatchResult matchedColor = m_colorMatcher.matchClosestColor(detectedColor);

        // SmartDashboard.putNumber("Detected Red", detectedColor.red);
        // SmartDashboard.putNumber("Detected Blue", detectedColor.blue);
        // SmartDashboard.putNumber("Detected Green", detectedColor.green);

        // SmartDashboard.putNumber("Matched Red", matchedColor.color.red);
        // SmartDashboard.putNumber("Matched Green", matchedColor.color.green);
        // SmartDashboard.putNumber("Matched Blue", matchedColor.color.blue);
        // SmartDashboard.putNumber("Confidence", matchedColor.confidence);

        if(matchedColor.confidence > 0.92)
        {
            if(matchedColor.color.red >= 0.3){
                SmartDashboard.putString("Matched Color", "Red");
                return ColorType.Red;
            } else if(matchedColor.color.red < 0.3){
                SmartDashboard.putString("Matched Color", "Blue");
                return ColorType.Blue;
            }
        } else {
            SmartDashboard.putString("Matched Color", "Non");
            return ColorType.None;
        }
        return ColorType.None;
    }

    /** Assumes that it detects a ball and returns true or false if it matches driverstation color */
    public boolean ballIsForTeam() {
        if(DriverStation.getAlliance() == DriverStation.Alliance.Red) {
            return readColor() == ColorType.Red;
        }
        else if(DriverStation.getAlliance() == DriverStation.Alliance.Blue) {
            return readColor() == ColorType.Blue;
        }
        else {
            return true;
        }
    }
    
    @Override
    public void periodic() {
        readColor();
    }
}