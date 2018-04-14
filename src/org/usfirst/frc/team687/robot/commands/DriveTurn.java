package org.usfirst.frc.team687.robot.commands;

import org.usfirst.frc.team687.robot.Robot;
import org.usfirst.frc.team687.robot.constants.Constants;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveTurn extends Command {
	private double m_angle;
	private double m_error;
	private double m_output;

    public DriveTurn(double angle) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drive);
    	m_angle = angle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	m_error = m_angle - Robot.drive.getYaw();
    	
    	if(m_error < -180) {
    		m_error = m_error + 180;
    	}
    	
    	if(m_error > 180) {
    		m_error = m_error - 180;
    	}
    	
    	m_output = m_error * Constants.kDriveP;
    	Robot.drive.setPower(m_output, m_output);
    
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(m_error) < Constants.kDriveTurnTolerance;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
