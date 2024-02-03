package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.geometry.Vector2d;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Auto.DriveAutoCore;

@Disabled
@Autonomous
public class PIDF extends LinearOpMode {
    double iSum = 0;
    double Kp = Constants.Kp;
    double Ki = Constants.Ki;
    double Kd = Constants.Kd;

    PIDController Xcon = new PIDController(Kp, Ki, Kd);
    double target = Constants.target;

    DriveAutoCore driveAutoCore = new DriveAutoCore();
    ElapsedTime time = new ElapsedTime();
    double lError = 0;
    double err = 3;
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        driveAutoCore.init(hardwareMap);
        driveAutoCore.allMotorMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveAutoCore.imuparams = new IMU.Parameters(new RevHubOrientationOnRobot
                (RevHubOrientationOnRobot.LogoFacingDirection.UP,
                        RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD));
        waitForStart();
        while (opModeIsActive() && Math.abs(err) > 2){
            telemetry.addData("Target: ", target);
            telemetry.addData("Current: ", driveAutoCore.backLeft.getCurrentPosition());
            double power = PIDControl(target, driveAutoCore.backLeft.getCurrentPosition());
            driveAutoCore.allMotorPower(power);
            telemetry.update();
        }
    }

    public double PIDControl(double ref, double cur){
        err = ref - cur;
        telemetry.addData("Error: ", err);
        iSum += err * time.seconds();
        double der = (err - lError) / time.seconds();
        lError = err;

        time.reset();
        double output = (err * Kp) + (der * Kd) + (iSum * Ki);
        return output;
    }
}
