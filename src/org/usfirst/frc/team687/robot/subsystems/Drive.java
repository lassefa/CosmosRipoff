package org.usfirst.frc.team687.robot.subsystems;

import org.usfirst.frc.team687.robot.Robot;
import org.usfirst.frc.team687.robot.RobotMap;
import org.usfirst.frc.team687.robot.commands.TankDrive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Drive extends Subsystem {

    private final TalonSRX frontLeftMotor;
    private final TalonSRX midLeftMotor;
    private final TalonSRX backLeftMotor;
    private final TalonSRX frontRightMotor;
    private final TalonSRX midRightMotor;
    private final TalonSRX backRightMotor;
    
    private final AHRS gyro;
    
    public Drive() {
    	frontLeftMotor = new TalonSRX(RobotMap.kFrontLeftMotor);
    	midLeftMotor = new TalonSRX(RobotMap.kMidLeftMotor);
    	backLeftMotor = new TalonSRX(RobotMap.kBackLeftMotor);
    	frontRightMotor = new TalonSRX(RobotMap.kFrontRightMotor);
    	midRightMotor = new TalonSRX(RobotMap.kMidRightMotor);
    	backRightMotor = new TalonSRX(RobotMap.kBackRightMotor);
    	
    	gyro = new AHRS(SPI.Port.kMXP);
    	
    	frontLeftMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    	frontRightMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    	
    	frontLeftMotor.setSensorPhase(true);
    	frontRightMotor.setSensorPhase(false);
    	
    	frontLeftMotor.configOpenloopRamp(0.118, 0);
    	midLeftMotor.configOpenloopRamp(0.118, 0);
    	backLeftMotor.configOpenloopRamp(0.118, 0);
    	frontRightMotor.configOpenloopRamp(0.118, 0);
    	midRightMotor.configOpenloopRamp(0.118, 0);
    	backRightMotor.configOpenloopRamp(0.118, 0);
    	
    	midLeftMotor.follow(frontLeftMotor);
    	backLeftMotor.follow(frontLeftMotor);
    	midRightMotor.follow(frontRightMotor);
    	backRightMotor.follow(frontRightMotor);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new TankDrive());
    }
    
    public void setPower(double leftPower, double rightPower) {
    	frontLeftMotor.set(ControlMode.PercentOutput, leftPower);
    	midLeftMotor.set(ControlMode.PercentOutput, leftPower);
    	backLeftMotor.set(ControlMode.PercentOutput, leftPower);
    	frontRightMotor.set(ControlMode.PercentOutput, -rightPower);
    	midRightMotor.set(ControlMode.PercentOutput, -rightPower);
    	backRightMotor.set(ControlMode.PercentOutput, -rightPower);
    }
    
    public double getLeftPosition() {
    	return frontLeftMotor.getSelectedSensorPosition(0);
    }
    
    public double getRightPosition() {
    	return frontRightMotor.getSelectedSensorPosition(0);    	
    }
    
    public double getAvgPosition() {
    	return (frontLeftMotor.getSelectedSensorPosition(0) + frontRightMotor.getSelectedSensorPosition(0) / 2);    	
    }
    
    public double getYaw() {
    	return gyro.getYaw();
    }
    
    public void resetEncoders() {
    	frontLeftMotor.setSelectedSensorPosition(0, 0, 0);
    	frontRightMotor.setSelectedSensorPosition(0, 0, 0);
    }
    
    public void resetGyro() {
    	gyro.reset();
    }
    
    public double getLeftVoltage() {
    	return frontLeftMotor.getMotorOutputVoltage();
    }
    
    public double getRightVoltage() {
    	return frontRightMotor.getMotorOutputVoltage();
    }
    
    public void reporttoDashboard() {
    	SmartDashboard.putNumber("Left Position", getLeftPosition());
    	SmartDashboard.putNumber("Right Postion", getRightPosition());
    	SmartDashboard.putNumber("Average Position", getAvgPosition());
    	SmartDashboard.putNumber("Yaw", getYaw());
    	SmartDashboard.putNumber("Left Power", getLeftVoltage());
    	SmartDashboard.putNumber("Right Power", getRightVoltage());
    }
    
   
    
}

