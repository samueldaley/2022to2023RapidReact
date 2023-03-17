package cwtech.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DashboardEntry {
    private final String m_key;
    public DashboardEntry(String key) {
        m_key = key;
    }

    public DashboardEntry(String key, int value) {
        m_key = key;
        putNumber(value);
    }

    public DashboardEntry(String key, String value) {
        m_key = key;
        putString(value);
    }

    public DashboardEntry(String key, boolean value) {
        m_key = key;
        putBoolean(value);
    }
    
    public void putNumber(double value) {
        SmartDashboard.putNumber(m_key, value);
    }

    public double getNumber(double defaultValue) {
        return SmartDashboard.getNumber(m_key, defaultValue);
    }

    public void putString(String value) {
        SmartDashboard.putString(m_key, value);
    }

    public String getString(String defaultValue) {
        return SmartDashboard.getString(m_key, defaultValue);
    }

    public void putBoolean(boolean value) {
        SmartDashboard.putBoolean(m_key, value);
    }

    public boolean getBoolean(boolean defaultValue) {
        return SmartDashboard.getBoolean(m_key, defaultValue);
    }
}