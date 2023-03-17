package frc.robot;

// Java do code sa ateema

import cwtech.util.Conditioning;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ExtendClimbHooksCommand;
import frc.robot.commands.FireCommandWithTracking;
import frc.robot.commands.GloryShotCommand;
import frc.robot.commands.IntakeToggleCommand;
import frc.robot.commands.LowGoalWallShotCommandGroup;
import frc.robot.commands.RetractClimbHooksCommand;
import frc.robot.commands.ReverseAccelaratorCommand;
import frc.robot.commands.ReverseIntakeCommand;
import frc.robot.commands.RotateClimbHooksBackCommand;
import frc.robot.commands.RotateClimbHooksForwardCommand;
import frc.robot.commands.SetHoodAngleCommand;
import frc.robot.commands.TraverseExtendClimbHooksCommand;
import frc.robot.commands.TuneShooterAndHoodCommand;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ColorSensor.ColorType;

public class OI {
    public static final double kDriverXdeadband = 0.18;
    public static final double kDriverXexponent = 1.7;
    public static final double kDriverYdeadband = 0.15;
    public static final double kDriverYexponent = 1.4;
    public static final double kDriverRotationDeadband = 0.2;
    public static final double kDriverRotationExponent = 0.9;

    private Joystick m_leftJoystick;
    private Joystick m_rightJoystick;
    private Joystick m_buttonBox;
    private XboxController m_xboxController;
    private final RobotContainer m_container;

    private Conditioning m_xConditioning;
    private Conditioning m_yConditioning;
    private Conditioning m_rotationConditioning;

    private final JoystickButton m_toggleIntakeButton;
    private final JoystickButton m_reverseIntakeButton;
    
    private final JoystickButton m_fireButton;
    private final JoystickButton m_hoodDownButton;
    private final JoystickButton m_testButton;
    private final JoystickButton m_safeZoneShotButton;
    private final JoystickButton m_wallShotButton;
    private final JoystickButton m_gloryShotButton;
    private final JoystickButton m_reverseAccButton;
    private final JoystickButton m_hoodUpButton;
    private final JoystickButton m_fireOverride;
    private final JoystickButton m_toggleIntakeButtonLeft;

    private final JoystickButton m_extendClimbHooksButton;
    private final JoystickButton m_retractClimbHooksButton;
    private final Button m_traverseRetractClimbHooksButton;
    private final Button m_rotateClimbHooksBackButton;
    private final Button m_rotateClimbHooksForwardButton;
    private final Button m_traverseExtendClimbHooksButton;
    private final Button m_stopCargoIndexerButton;
    private final JoystickButton m_autoGrabTraverseButton;

    public OI(RobotContainer container) {
        m_container = container;

        m_xConditioning = new Conditioning();
        m_yConditioning = new Conditioning();
        m_rotationConditioning = new Conditioning();

        m_xConditioning.setDeadband(kDriverXdeadband);
        m_xConditioning.setExponent(kDriverXexponent);
        m_yConditioning.setDeadband(kDriverYdeadband);
        m_yConditioning.setExponent(kDriverYexponent);
        m_rotationConditioning.setDeadband(kDriverRotationDeadband);
        m_rotationConditioning.setExponent(kDriverRotationExponent);

        m_leftJoystick = new Joystick(RobotMap.kLeftJoystick);
        m_rightJoystick = new Joystick(RobotMap.kRightJoystick);
        m_buttonBox = new Joystick(RobotMap.kButtonBox);
        m_xboxController = new XboxController(RobotMap.kXBoxController);

        m_toggleIntakeButton = new JoystickButton(m_rightJoystick, RobotMap.kToggleIntakeButton);
        m_reverseIntakeButton = new JoystickButton(m_buttonBox, RobotMap.kReverseIntakeButton);

        m_stopCargoIndexerButton = new Button(() -> m_container.colorSensor.readColor() != ColorType.None && m_container.intake.isExtended());
     
        m_fireButton = new JoystickButton(m_buttonBox, RobotMap.kFireButton);
        m_hoodDownButton = new JoystickButton(m_buttonBox, RobotMap.kHoodDownButton);
        m_testButton = new JoystickButton(m_buttonBox, RobotMap.kTestButton);
        m_safeZoneShotButton = new JoystickButton(m_buttonBox, RobotMap.kSafeZoneShotButton);
        m_wallShotButton = new JoystickButton(m_buttonBox, RobotMap.kWallShotButton);
        m_gloryShotButton = new JoystickButton(m_buttonBox, RobotMap.kGloryShotButton);
        m_reverseAccButton = new JoystickButton(m_buttonBox, RobotMap.kReverseAccButton);
        m_hoodUpButton = new JoystickButton(m_buttonBox, RobotMap.kHoodUpButton);
        m_fireOverride = new JoystickButton(m_buttonBox, RobotMap.kFireOverrideButton);
        m_toggleIntakeButtonLeft = new JoystickButton(m_leftJoystick, 1);
        m_autoGrabTraverseButton = new JoystickButton(m_xboxController, 6);
        
        m_rotateClimbHooksForwardButton = new Button(() -> {
            return
             m_xboxController.getRawButton(RobotMap.kClimbSafetyButton) &&
             m_xboxController.getLeftY() > 0.5;
        });
        m_rotateClimbHooksBackButton = new Button(() -> {
            return
             m_xboxController.getRawButton(RobotMap.kClimbSafetyButton) &&
             m_xboxController.getLeftY() < -0.5;
        });
        m_traverseExtendClimbHooksButton = new Button(() -> {
            return
            m_xboxController.getRawButton(RobotMap.kClimbSafetyButton) &&
            m_xboxController.getRightY() < -0.5;
        });
        m_traverseRetractClimbHooksButton = new Button(() -> {
            return
            m_xboxController.getRawButton(RobotMap.kClimbSafetyButton) &&
            m_xboxController.getRightY() > 0.5;
        });

        // m_autoGrabTraverseButton.whenPressed(
            // new ExtendClimbHooksCommand(m_container).alongWith(new InstantCommand(() -> m_container.climb.setRotatorPosition(Climb.kExtendRotatorPosition * 0.5))));
            // new InstantCommand(() -> m_container.climb.setRotatorPosition(Climb.kExtendRotatorPosition * 0.5)));

        m_stopCargoIndexerButton.whenPressed(new InstantCommand(() -> m_container.cargoIndexer.setSpeed(0)));

        m_extendClimbHooksButton = new JoystickButton(m_buttonBox, RobotMap.kExtendClimbHooksButton);
        m_retractClimbHooksButton = new JoystickButton(m_buttonBox, RobotMap.kRetractClimbHooksButton);
 
        m_toggleIntakeButton.whenPressed(new IntakeToggleCommand(m_container));
        m_toggleIntakeButtonLeft.whenPressed(new IntakeToggleCommand(m_container));
        m_reverseIntakeButton.whileHeld(new ReverseIntakeCommand(m_container));
        // m_fireButton.whenPressed(new FireCommand(m_container));
        m_fireButton.whenPressed(new FireCommandWithTracking(m_container));
        m_hoodDownButton.whenPressed(new SetHoodAngleCommand(m_container, Hood.kMinDegrees));
        // m_safeZoneShotButton.whenPressed(new SafeZoneShotCommandGroup(m_container));
        // m_safeZoneShotButton.whenPressed(new TurnTurretToPositionCommand(m_container, 0.0));
        m_safeZoneShotButton.whenPressed(new TuneShooterAndHoodCommand(m_container));
        m_wallShotButton.whenPressed(new LowGoalWallShotCommandGroup(m_container));
        m_testButton.whenPressed(new InstantCommand(() -> {
            m_container.shooter.setVelocity(Shooter.kIdleSpeed);
        }, m_container.shooter));
        // m_testButton.whenPressed(new TurnTurretToPositionCommand(m_container, 0.0));
        m_gloryShotButton.whenPressed(new GloryShotCommand(m_container));
        m_reverseAccButton.whileHeld(new ReverseAccelaratorCommand(m_container));
        m_hoodUpButton.whenPressed(new SetHoodAngleCommand(m_container, 1));

        m_extendClimbHooksButton.whenPressed(new ExtendClimbHooksCommand(m_container));
        m_retractClimbHooksButton.whileHeld(new RetractClimbHooksCommand(m_container));
        m_rotateClimbHooksForwardButton.whileHeld(new RotateClimbHooksForwardCommand(m_container));
        m_rotateClimbHooksBackButton.whileHeld(new RotateClimbHooksBackCommand(m_container));
        m_traverseRetractClimbHooksButton.whileHeld(new RetractClimbHooksCommand(m_container));
        m_traverseExtendClimbHooksButton.whileHeld(new TraverseExtendClimbHooksCommand(m_container));
    }

    public boolean getFireOverrided() {
        return m_fireOverride.getAsBoolean();
    }

    public double getDriveSpeedX() {
        return m_xConditioning.condition(m_leftJoystick.getY()) * Drivetrain.kMaxSpeedMetersPerSecond;
    }

    public double getDriveSpeedY() {
        return m_yConditioning.condition(m_leftJoystick.getX()) * Drivetrain.kMaxSpeedMetersPerSecond;
    }

    public double getDriveSpeedRotation() {
        return m_rotationConditioning.condition(m_rightJoystick.getX()) * Drivetrain.kMaxAngularSpeedRadiansPerSecond;
    }

    public void onDisabledInit() {
        // m_xConditioning.setExponent(SmartDashboard.getNumber(DashboardMap.kOIXExponent, kDriverXexponent));
        // m_xConditioning.setDeadband(SmartDashboard.getNumber(DashboardMap.kOIXDeadband, kDriverXdeadband));
        // m_yConditioning.setExponent(SmartDashboard.getNumber(DashboardMap.kOIYExponent, kDriverYexponent));
        // m_yConditioning.setDeadband(SmartDashboard.getNumber(DashboardMap.kOIYDeadband, kDriverYdeadband));
        // m_rotationConditioning.setExponent(SmartDashboard.getNumber(DashboardMap.kOITurnExponent, kDriverRotationExponent));
        // m_rotationConditioning.setDeadband(SmartDashboard.getNumber(DashboardMap.kOITurnDeadband, kDriverRotationDeadband));
    }
}
