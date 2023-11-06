package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class ShawnDrive extends LinearOpMode {
    DcMotorEx frontleft, frontright, backleft, backright, armMotor;

    Servo lClaw, rClaw;

    public double reducerArm = 0.4;
    public double armPower;

    boolean clawstat;
    Gamepad currentGamepad = new Gamepad();
    Gamepad previousGamepad = new Gamepad();

    Gamepad currentGamepad2 = new Gamepad();
    Gamepad previousGamepad2 = new Gamepad();

    Gamepad.RumbleEffect effect;

    double reducer = 0.75;

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
        armPower = (-gamepad2.right_trigger + gamepad2.left_trigger) * reducerArm;
        armMotor.setPower(armPower);
    }

    public void servoGo(){
        try {
            edgeDetector();
        } catch (RobotCoreException e) {
            throw new RuntimeException(e);
        }
        if(currentGamepad.a && !previousGamepad.a){
            clawstat = !clawstat;
            if(clawstat){
                rClaw.setPosition(0.12);
                lClaw.setPosition(0.23);
            } else {
                rClaw.setPosition(0.25);
                lClaw.setPosition(0.10);
            }
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
        double Horizontal = gamepad1.left_stick_x * 1.2;
        double Pivot = gamepad1.right_stick_x;

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
        armMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        effect = new Gamepad.RumbleEffect.Builder()
                .addStep(1.0, 1.0, 2000)

                .build();

        frontleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontright.setDirection(DcMotorSimple.Direction.FORWARD);
        frontleft.setDirection(DcMotorSimple.Direction.REVERSE);
        backright.setDirection(DcMotorSimple.Direction.FORWARD);
        backleft.setDirection(DcMotorSimple.Direction.FORWARD);

        frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        rClaw.setPosition(0.12);
        lClaw.setPosition(0.23);
    }

    public void edgeDetector() throws RobotCoreException {
        previousGamepad.copy(currentGamepad);
        currentGamepad.copy(gamepad1);
    }
}
