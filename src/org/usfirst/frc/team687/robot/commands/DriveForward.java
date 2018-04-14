package org.usfirst.frc.team687.robot.commands;

import org.usfirst.frc.team687.robot.Robot;
import org.usfirst.frc.team687.robot.constants.Constants;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveForward extends Command {
	private double m_distance;
	private double m_error;
	private double m_oldError;
	private double m_time;
	private double m_oldTime;
	private double m_output;
	private double m_derivative;
		
    public DriveForward(double distance) {
    	m_distance = distance;
    	requires(Robot.drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	m_oldError = Robot.drive.getAvgPosition();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	m_error = m_distance - Robot.drive.getAvgPosition();
    	m_time = Timer.getFPGATimestamp();
    	m_derivative = (m_error - m_oldError) /  (m_time - m_oldTime); 	
       	m_output = (m_error * Constants.kDriveP) + (m_derivative * Constants.kDriveD);
    	
    	Robot.drive.setPower(-m_output, -m_output);
    	
    	m_oldTime = m_time;
    	m_oldError = m_error;
    	
    	SmartDashboard.putNumber("Voltage", m_output);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {	
        // return m_distance < Math.abs(Robot.drive.getAvgPosition());
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drive.setPower(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
