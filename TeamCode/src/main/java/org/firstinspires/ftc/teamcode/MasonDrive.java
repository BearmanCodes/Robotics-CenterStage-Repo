package org.firstinspires.ftc.teamcode;

import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class MasonDrive extends LinearOpMode {
    DcMotorEx frontleft, frontright, backleft, backright, armMotor;

    Servo lClaw, rClaw;

    public double reducerArm = 0.25;
    public double armPower;

    boolean clawstat;
    Gamepad currentGamepad = new Gamepad();
    Gamepad previousGamepad = new Gamepad();

    Gamepad currentGamepad2 = new Gamepad();
    Gamepad previousGamepad2 = new Gamepad();

    Gamepad.RumbleEffect effect;

    double reducer = 0.8;

    @Override
    public void runOpMode() throws InterruptedException {
        Init();
        waitForStart();
        while (opModeIsActive()) {

            main();
            arm();
            servoGo();
        }
    }


    public void arm(){
        armPower = ((gamepad2.right_stick_y/*gamepad2.right_trigger + -gamepad2.left_trigger*/) * reducerArm) - 0.0025;
        armMotor.setPower(armPower);
        telemetry.addData("Current Position arm: ", armMotor.getCurrentPosition());
        telemetry.addData("Current arm power: ", armPower);
        telemetry.update();
    }

    public void servoGo(){
        try {
            edgeDetector();
        } catch (RobotCoreException e) {
            throw new RuntimeException(e);
        }/*
        if(currentGamepad2.a && !previousGamepad2.a){
            clawstat = !clawstat;
            if(clawstat){
                rClaw.setPosition(0.12);
                lClaw.setPosition(0.23);
            } else {
                rClaw.setPosition(0.33);
                lClaw.setPosition(0.07);
            }
        }
        */
        if (currentGamepad2.dpad_up && !previousGamepad2.dpad_up){
            lClaw.setPosition(0.96); //(close)
            rClaw.setPosition(1.00); //(close)
        }
        if (currentGamepad2.dpad_down && !previousGamepad2.dpad_down){
            lClaw.setPosition(0.8); //(open)
            rClaw.setPosition(0.8); //(open)
        }
    }

    public void allMotorPower(double power){
        frontright.setPower(power);
        frontleft.setPower(power);
        backleft.setPower(power);
        backright.setPower(power);
    }

    public void main(){
        double Vertical = gamepad1.left_stick_y;
        double Horizontal = gamepad1.left_stick_x;
        double Pivot = gamepad1.right_stick_x;
        double denominator = Math.max(Math.abs(Vertical) + Math.abs(Horizontal) + Math.abs(Pivot), 1);

        double frontLeftPower = (-Pivot + (Vertical - Horizontal)) * reducer;
        double frontRightPower = (Pivot + Vertical + Horizontal) * reducer;
        double backRightPower = (Pivot + (Vertical - Horizontal)) * reducer;
        double backLeftPower = (-Pivot + Vertical + Horizontal) * reducer;
        /*Brake code Klarissa added  while (gamepad1.circle){
            allMotorPower(0);

            make go back
            if (gamepad1.right_bumper){
                frontleft.setDirection(DcMotorSimple.Direction.REVERSE);
                frontright.setDirection(DcMotorSimple.Direction.FORWARD);
                backleft.setDirection(DcMotorSimple.Direction.REVERSE);
                backright.setDirection(DcMotorSimple.Direction.REVERSE);
            }
        }
        */
        frontleft.setPower(frontLeftPower);
        frontright.setPower(frontRightPower);
        backleft.setPower(backLeftPower);
        backright.setPower(backRightPower);

    }

    private void Init(){
        frontleft = hardwareMap.get(DcMotorEx.class, "frontleft");
        frontright = hardwareMap.get(DcMotorEx.class, "frontright");
        backleft = hardwareMap.get(DcMotorEx.class, "backleft");
        backright = hardwareMap.get(DcMotorEx.class, "backright");
        rClaw = hardwareMap.get(Servo.class, "rclaw".toLowerCase());
        lClaw = hardwareMap.get(Servo.class, "lclaw".toLowerCase());

        armMotor = hardwareMap.get(DcMotorEx.class, "left".toLowerCase());
        effect = new Gamepad.RumbleEffect.Builder()
                .addStep(1.0, 1.0, 2000)

                .build();

        frontleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        frontright.setDirection(DcMotorSimple.Direction.FORWARD);
        frontleft.setDirection(DcMotorSimple.Direction.REVERSE);
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

        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        rClaw.setPosition(0.10);
        lClaw.setPosition(0.13);

        lClaw.setDirection(Servo.Direction.REVERSE);

        SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, R.raw.sound);
    }

    public void edgeDetector() throws RobotCoreException {
        previousGamepad.copy(currentGamepad);
        currentGamepad.copy(gamepad1);
        previousGamepad2.copy(currentGamepad2);
        currentGamepad2.copy(gamepad2);
    }
}
