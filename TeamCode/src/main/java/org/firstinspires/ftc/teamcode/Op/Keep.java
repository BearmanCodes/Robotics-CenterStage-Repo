package org.firstinspires.ftc.teamcode.Op;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Auto.DriveAutoCore;


@TeleOp
public class Keep extends LinearOpMode {
    DrivetrainCore dTrain = new DrivetrainCore();
    ServoCore servoCore = new ServoCore();
    DriveAutoCore dAuto = new DriveAutoCore();
    static final double TicksPerRev = 560;
    static final double WheelInches = (75 / 25.4);
    static final double TicksPerIn = TicksPerRev / (WheelInches * Math.PI);
    @Override
    public void runOpMode() throws InterruptedException {
        Init();
        waitForStart();
        servoCore.time.reset();
        while (opModeIsActive()) {
            try {
                servoCore.edgeDetector(gamepad1, gamepad2);
            } catch (RobotCoreException e) {
                throw new RuntimeException(e);
            }

            if(servoCore.currentGamepad.dpad_left && !servoCore.previousGamepad.dpad_left){
                dTrain.setMotorPower(-0.5, 0.5, 0.5, -0.5);
            } else if (servoCore.currentGamepad.dpad_right && !servoCore.previousGamepad.dpad_right){
                dTrain.setMotorPower(0.5, -0.5, -0.5, 0.5);
            } else if (servoCore.currentGamepad.dpad_up && !servoCore.previousGamepad.dpad_up){
                dTrain.setMotorPower(0.5, 0.5, 0.5, 0.5);
            } else if (servoCore.currentGamepad.dpad_down && !servoCore.previousGamepad.dpad_down){
                dTrain.setMotorPower(0.5, 0.5, 0.5, 0.5);
            } else {
                dTrain.setMotorPower(0, 0, 0, 0);
            }

            if (servoCore.currentGamepad.x && ! servoCore.previousGamepad.x){
                dAuto.turnAmount(90, opModeIsActive());
            }

            if (servoCore.currentGamepad.y && ! servoCore.previousGamepad.y){
                dAuto.turnAmount(-90, opModeIsActive());
            }

            if (servoCore.currentGamepad.a && !servoCore.previousGamepad.a){
                telemetry.addData("Pos: ", dTrain.frontleft.getCurrentPosition() / TicksPerIn);
                telemetry.update();
                dTrain.frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                dTrain.frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
        }
    }

    private void Init(){
        dTrain.init(hardwareMap);
    }
}
