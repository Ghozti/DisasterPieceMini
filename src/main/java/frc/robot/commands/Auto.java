// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.sql.Driver;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.RomiDrivetrain;

public class Auto extends CommandBase {

  RomiDrivetrain drivetrain;
  Timer timer = new Timer();

  private double error;//feetTarget - encoder position
  private double output;// kP*error

  private final int inchTarget = 60 ;
  private final double kP = 0.3;
  private final double kI = 0;
  private final double kD = 0;

  /** Creates a new Auto. */
  public Auto(RomiDrivetrain drivetrain) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
    this.drivetrain = drivetrain;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drivetrain.resetEncoders();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    error = inchTarget - (drivetrain.getRightDistanceInch() + drivetrain.getLeftDistanceInch())/2;
    output = kP * error;

    drivetrain.m_diffDrive.arcadeDrive(output, 0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.m_diffDrive.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return error <= 0;
  }
}
