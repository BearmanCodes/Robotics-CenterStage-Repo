package org.firstinspires.ftc.teamcode.Op;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;


public class DrivetrainCore{

    public DcMotorEx frontleft, frontright, backleft, backright;

    public double reducer = 1; //Change for reducing drive power

    YawPitchRollAngles robotOrientation;

    IMU imu;
    IMU.Parameters imuparams;

    public void init(HardwareMap hwMap){
        frontleft = hwMap.get(DcMotorEx.class, "frontleft");  //change these motor names depending on the config
        frontright = hwMap.get(DcMotorEx.class, "frontright");
        backleft = hwMap.get(DcMotorEx.class, "backleft");
        backright = hwMap.get(DcMotorEx.class, "backright");

        imu = hwMap.get(IMU.class, "imu");
        imuparams = new IMU.Parameters(new RevHubOrientationOnRobot
                (RevHubOrientationOnRobot.LogoFacingDirection.UP,
                        RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD));

        imu.initialize(imuparams);
        imu.resetYaw();

        motorSetUp();

    }

    public void run(Gamepad gamepad1){
        double Vertical = gamepad1.left_stick_y;
        double Horizontal = gamepad1.left_stick_x;
        double Pivot = gamepad1.right_stick_x;
        double denominator = Math.max(Math.abs(Vertical) + Math.abs(Horizontal) + Math.abs(Pivot), 1);

        double frontLeftPower = (-Pivot + (Vertical - Horizontal)) * reducer;
        double frontRightPower = (Pivot + Vertical + Horizontal) * reducer; //Mecanum drivetrain shenanigans
        double backRightPower = (Pivot + (Vertical - Horizontal)) * reducer;
        double backLeftPower = (-Pivot + Vertical + Horizontal) * reducer;

        frontleft.setPower(frontLeftPower);
        frontright.setPower(frontRightPower);
        backleft.setPower(backLeftPower);
        backright.setPower(backRightPower);
    }

    public void allMotorPower(double power){
        frontright.setPower(power);
        frontleft.setPower(power);
        backleft.setPower(power);
        backright.setPower(power);
    }

    public void motorSetUp(){
        frontleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontright.setDirection(DcMotorSimple.Direction.FORWARD);
        frontleft.setDirection(DcMotorSimple.Direction.REVERSE); //Change these directions for your drive
        backright.setDirection(DcMotorSimple.Direction.FORWARD);
        backleft.setDirection(DcMotorSimple.Direction.FORWARD);

        frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void setMotorVelocity(double fLvelocity, double fRvelocity, double bLvelocity, double bRvelocity) {
        frontleft.setVelocity(fLvelocity);
        frontright.setVelocity(fRvelocity);
        backleft.setVelocity(bLvelocity);
        backright.setVelocity(bRvelocity);
    }

    public void allMotorVelocity(double velocity) {
        frontleft.setVelocity(velocity);
        frontright.setVelocity(velocity);
        backleft.setVelocity(velocity);
        backright.setVelocity(velocity);
    }

    public void Flip(boolean active) {
        if (active) {
            robotOrientation = imu.getRobotYawPitchRollAngles();
            double Yaw = robotOrientation.getYaw(AngleUnit.DEGREES);
            double target = Yaw + 180;
            if (active) {
                if (target < 0) {
                    while (Yaw > target) {
                        setMotorVelocity(3000, -3000, 3000, -3000);
                        robotOrientation = imu.getRobotYawPitchRollAngles();
                        Yaw = robotOrientation.getYaw(AngleUnit.DEGREES);
                    }
                    allMotorVelocity(0);
                    if (Yaw < target) {
                        robotOrientation = imu.getRobotYawPitchRollAngles();
                        Yaw = robotOrientation.getYaw(AngleUnit.DEGREES);
                        double error = Yaw - target;
                        while (Math.abs(error) > 0.2) {
                            setMotorVelocity(-3000, 3000, -3000, 3000);
                            robotOrientation = imu.getRobotYawPitchRollAngles();
                            Yaw = robotOrientation.getYaw(AngleUnit.DEGREES);
                            error = Yaw - target;
                        }
                        allMotorVelocity(0);
                    }
                }
                if (target > 0) {
                    while (Yaw < target) {
                        setMotorVelocity(-3000, 3000, -3000, 3000);
                        robotOrientation = imu.getRobotYawPitchRollAngles();
                        Yaw = robotOrientation.getYaw(AngleUnit.DEGREES);
                    }
                    allMotorVelocity(0);
                    if (Yaw > target) {
                        robotOrientation = imu.getRobotYawPitchRollAngles();
                        Yaw = robotOrientation.getYaw(AngleUnit.DEGREES);
                        double error = Yaw - target;
                        while (Math.abs(error) > 0.2) {
                            setMotorVelocity(300, -300, 300, -300);
                            robotOrientation = imu.getRobotYawPitchRollAngles();
                            Yaw = robotOrientation.getYaw(AngleUnit.DEGREES);
                            error = Yaw - target;
                        }
                        allMotorVelocity(0);
                    }
                }
            }
        }
    }
}
