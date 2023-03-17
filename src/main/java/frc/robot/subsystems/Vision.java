package frc.robot.subsystems;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;

import cwtech.util.BasicTrajectory;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.DashboardMap;

public class Vision extends SubsystemBase {
    private final NetworkTableEntry m_txEntry;
    private final NetworkTableEntry m_tyEntry;
    private final NetworkTableEntry m_pipelineEntry;

    /** This is what the limelight's height when mounted */
    public static final double kCameraHeightMeters = 43.75 * 0.0254;
    /** This is what the limelight's angle when mounted */
    private static final double kCameraAngleDegrees = 30;

    public static final double kCameraTYOffset = 3.2;
    private static final double kHubRadius = (26 * 0.0254);
    public static final double kHubHeightMeters = (104 * 0.0254);
    public static final double kDifferenceMeters = kHubHeightMeters - kCameraHeightMeters;

    // private double kShootingMin;
    // private double kShootingMax;
    // private double kShootingRes;
    // private Vector<Pair<Double, Double>> kShootingMap;

    private Vector<Double> m_points = new Vector<>();
    private Vector<Double> m_velocities = new Vector<>();
    private Vector<Double> m_hoods = new Vector<>();

    public Vision() {
        m_txEntry = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx");
        m_tyEntry = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty");
        m_pipelineEntry = NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline");

        m_pipelineEntry.setNumber(0);

        setLedMode(0);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(Filesystem.getDeployDirectory().toPath().resolve("shooting.csv").toString()));
            // boolean setMin = false;
            // boolean setRes = false;
            boolean readHeader = false;
            String line;
            // kShootingMap = new Vector<>();
            // double lastDist = 0;

            while((line = reader.readLine()) != null) {
                if(!readHeader) {
                    readHeader = true;
                    continue;
                }
                String[] info = line.split(",");
                m_points.add(Double.parseDouble(info[0]));
                m_velocities.add(Double.parseDouble(info[1]));
                m_hoods.add(Double.parseDouble(info[2]));
                // lastDist = Double.parseDouble(info[0]);
                // if(setMin && !setRes) {
                //     setRes = true;
                //     kShootingRes = lastDist - kShootingMin;
                // }
                // if(!setMin) {
                //     setMin = true;
                //     kShootingMin = lastDist;
                // }
                // kShootingMap.add(new Pair<>(Double.parseDouble(info[1]), Double.parseDouble(info[2])));
            }
            // kShootingMax = lastDist;
        }
        catch(Exception e) {
            // kShootingMax = 0;
            // kShootingMin = 0;
            // kShootingRes = 0;
            // kShootingMap = new Vector<>();
            DriverStation.reportError("Failed to load shooting map file", e.getStackTrace());
        }

        // System.err.println(String.format("Min: %f, Max: %f, Res: %f", kShootingMin, kShootingMax, kShootingRes));
        // System.err.println(String.format("Best Index for 0.60: %d", getClosestIndex(0.60)));

        System.err.println(String.format("%d %d %d", m_points.size(), m_velocities.size(), m_hoods.size()));
        for(var i = 0; i < m_points.size(); i++) {
            System.err.println(String.format("%f %f %f", m_points.get(i), m_velocities.get(i), m_hoods.get(i)));
        }

        for(var i = 0.0; i <= 1; i += 0.1) {
            System.err.println(String.format("Distance: %f, Velocity: %f, Hood: %f", i, shooterVelocity(i), shooterAngleDegrees(i)));
        }
    }

    /** Gets the 'tx' from the limelight 
     * @return Horizontal offset from crosshair to target -29.8 to 29.8 degrees
    */
    public double getTX() {
        return (double) m_txEntry.getNumber(0.0);
    }

    /** Gets the 'ty' from the limelight 
     * @return Vertical offset from cross to target -24.85 to 24.85 degrees
    */
    public double getTY() {
        return (double) m_tyEntry.getNumber(0.0) - SmartDashboard.getNumber(DashboardMap.kTrajectoryTyOffset, Vision.kCameraTYOffset);
    }

    /** Calculates distance from a target.
     * 
     * @param heightMeters Height of the target in meters
     * @return Distance from target in meters
    */
    private double getDistanceFromTargetWithHeight(double heightMeters) {
        double a2 = getTY();
        return (heightMeters - kCameraHeightMeters) / BasicTrajectory.tan(kCameraAngleDegrees + a2);
    }

    public double getDistanceToHubCenterWithHeight(double heightMeters) {
        return getDistanceFromTargetWithHeight(heightMeters) + kHubRadius;
    }

    public void setLedMode(int mode) {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(mode);
    }

    public int ledMode() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").getNumber(0).intValue();
    }

    @Override
    public void periodic() {
        if(ledMode() == 2) { //if force blink
            return;
        }
        else if(DriverStation.isEnabled()) {
            setLedMode(0); //normal
        }
        else {
            setLedMode(1); //force off
        }
        // SmartDashboard.putNumber("Vision/Distance(Meters)", getDistanceToHubCenterWithHeight(Vision.kHubHeightMeters));
    }
    // public int getClosestIndex(double distance) {
    //     if(distance > kShootingMax) { 
    //         return kShootingMap.size();
    //     }
    //     else if(distance < kShootingMin) {
    //         return 0;
    //     }
    //     return (int) ((Math.round(distance / kShootingRes)) + 1 - kShootingMin);
    // }

    // public double getShooterSpeed(int index) {
    //     return kShootingMap.get(index).getFirst();
    // }

    // public double getHoodPosition(int index) {
    //     return kShootingMap.get(index).getSecond();
    // }

    public double shooterVelocity(double distance){

        // var points = m_points; //new Vector<Double>();
        // points.add(.25);
        // points.add(.5);
        // points.add(.75);
        // points.add(1.);

        // Vector<Double> velocities = m_velocities;//new Vector<>();
        // velocities.add(1000.0);
        // velocities.add(2000.0);
        // velocities.add(4000.0);
        // velocities.add(5000.0);

        for(int i = 0; i < m_points.size() - 1; i++){
            if(distance >= m_points.get(i) && distance < m_points.get(i + 1)){
                double p = (distance - m_points.get(i)) / (m_points.get(i + 1) - m_points.get(i));
                return p * (m_velocities.get(i + 1) - m_velocities.get(i)) + m_velocities.get(i);
            }
            if(distance == m_points.get(m_points.size() - 1)){
                return m_velocities.get(m_velocities.size() - 1);
            }
        }
        return 0;
    }

    public double shooterAngleDegrees(double distance){
        
        for(int i = 0; i < m_points.size() - 1; i++){
            if(distance >= m_points.get(i) && distance < m_points.get(i + 1)){
                double p = (distance - m_points.get(i) / (m_points.get(i + 1) - m_points.get(i)));
                return p * (m_hoods.get(i + 1) - m_hoods.get(i)) + m_hoods.get(i);
            }
            if(distance == m_points.get(m_points.size() - 1)){
                return m_hoods.get(m_hoods.size() - 1);
            }
        }
        return 0;
    }

    
}
