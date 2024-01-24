package org.firstinspires.ftc.teamcode.Op;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.checkerframework.checker.units.qual.A;
import org.firstinspires.ftc.teamcode.Auto.DriveAutoCore;


@TeleOp(name = "AutoGuide")
public class Keep extends LinearOpMode {
    DrivetrainCore dTrain = new DrivetrainCore();
    ServoCore servoCore = new ServoCore();
    ArmCore armCore = new ArmCore();
    DriveAutoCore dAuto = new DriveAutoCore();
    static final double TicksPerRev = 560;
    static final double WheelInches = (75 / 25.4);
    static final double TicksPerIn = TicksPerRev / (WheelInches * Math.PI);
    
    double mPower = 0.1;
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

            //arm down 979
            //drive forward 10.5
            // strafe right 14.5-7
            //arm 705
            //20.85 forward
            //strafe left 9.5

            //21 inches forward
            //9.6 inches strafe right
            //2 inches strafe left
            //20.85 + 6.9 to go to backboard


            driveDpad();
            armCore.rStick(gamepad1);
            servoCore.dpadRun(servoCore.currentGamepad, servoCore.previousGamepad);

            if (servoCore.currentGamepad.start && !servoCore.previousGamepad.start){
                telemetry.addData("Drive Pos: ", dTrain.frontleft.getCurrentPosition() / TicksPerIn);
                telemetry.addData("Arm Pos: ", armCore.actualArm.getCurrentPosition());
                telemetry.update();
                dTrain.frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                dTrain.frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                armCore.actualArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
        }
    }

    public void driveDpad(){
        if(gamepad1.dpad_left){
            dTrain.setMotorPower(-mPower, mPower, mPower, -mPower);
        } else if (gamepad1.dpad_right){
            dTrain.setMotorPower(mPower, -mPower, -mPower, mPower);
        } else if (gamepad1.dpad_up){
            dTrain.setMotorPower(mPower, mPower, mPower, mPower);
        } else if (gamepad1.dpad_down){
            dTrain.setMotorPower(-mPower, -mPower, -mPower, -mPower);
        } else {
            dTrain.setMotorPower(0, 0, 0, 0);
        }

        if (servoCore.currentGamepad.right_bumper && ! servoCore.previousGamepad.right_bumper){
            dAuto.turnAmount(90, opModeIsActive());
        }

        if (servoCore.currentGamepad.left_bumper && ! servoCore.previousGamepad.left_bumper){
            dAuto.turnAmount(-90, opModeIsActive());
        }
    }

    private void Init(){
        dTrain.init(hardwareMap);
        armCore.init(hardwareMap);
        servoCore.init(hardwareMap);
    }
}
