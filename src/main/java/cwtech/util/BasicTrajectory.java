package cwtech.util;

public class BasicTrajectory {
    /** Class to give exit velocity(degrees) and shooting angle(degrees) */
    static public class TrajectoryCalculation {
        public final double m_shootingAngleDegrees;
        public final double m_exitVelocityMetersPerSecond;

        public TrajectoryCalculation(double shootingAngleDegrees, double exitVelocityMetersPerSecond) {
            m_shootingAngleDegrees = shootingAngleDegrees;
            m_exitVelocityMetersPerSecond = exitVelocityMetersPerSecond;
        }
    }

    private BasicTrajectory() {}

    /** Calculates the exit velocity and the shooting angle for the shooter
     * @param S angle(degrees) you want to hit the target in
     * @param d horizontal distance(meters) betweeen the shooter and the upper hub
     * @param H height difference(meters) between the shooter and the upper hub
     * 
     * Based on: https://www.desmos.com/calculator/mdxtrnxps5
     */
    static public TrajectoryCalculation calculate(double S, double d, double H) {
        double a = atan(((tan(S) * d) - (2.0 * H)) / -d);
        double v = Math.sqrt(-( ((9.8 * (Math.pow(d, 2.0))) * (1.0 + Math.pow(tan(a), 2.0))) / ((2.0 * H) - (2.0 * d * tan(a))) ));

        /*
        double p1 = tan(S);
        p1 = p1 * d;
        p1 = p1 - (2.0 * H);

        double a = atan(p1 / -d);

        double p2 = Math.pow(d, 2);
        p2 = p2 * 9.8;

        double p3 = Math.pow(tan(a), 2);
        p3 = 1 + p3;

        p2 = p2 * p3;

        double p4 = 2 * H;
        double p5 = 2 * d * tan(a);
        p4 = p4 - p5;

        double v = Math.sqrt(-(p2 / p4));*/

        return new TrajectoryCalculation(a, v);
    }

    /** Wrapper that allows degrees for the Math.tan function */
    static public double tan(double degrees) {
        return Math.tan(Math.toRadians(degrees));
    }

    /** Wrapper that returns degrees for the Math.atan function */
    static public double atan(double tangent) {
        return Math.toDegrees(Math.atan(tangent));
    }
}
