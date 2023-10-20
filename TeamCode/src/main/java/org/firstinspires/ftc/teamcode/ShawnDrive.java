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
    DcMotorEx frontleft;
    DcMotorEx frontright;
    DcMotorEx backleft;
    DcMotorEx backright;

    Servo leftServo;

    Servo rightServo;

    private DcMotorEx left, right;

    public double reducerArm = 0.4;
    public double armPower;

    Gamepad currentGamepad = new Gamepad();
    Gamepad previousGamepad = new Gamepad();

    Gamepad.RumbleEffect effect;

    double reducer = 0.75;

    @Override
    public void runOpMode() throws InterruptedException {
        Init();
        waitForStart();
        while (opModeIsActive()) {
            try {
                edgeDetector();
            } catch (RobotCoreException e) {
                throw new RuntimeException(e);
            }
            main();
            arm();
            if (currentGamepad.cross && !previousGamepad.cross){
                leftServo.setPosition(0.25);
                rightServo.setPosition(1);
            }
            if (currentGamepad.circle && !previousGamepad.circle){
                leftServo.setPosition(0.6);
                rightServo.setPosition(0.7);
            }
        }
    }

    public void arm(){
        armPower = (-gamepad2.left_trigger + gamepad2.right_trigger) * reducerArm;
        left.setPower(armPower);
        right.setPower(armPower);
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
        /*Brake code Klarissa added */ while (gamepad1.circle){
            allMotorPower(0);
        }
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
        rightServo = hardwareMap.get(Servo.class, "servoRight".toLowerCase());
        leftServo = hardwareMap.get(Servo.class, "servoLeft".toLowerCase());

        left = hardwareMap.get(DcMotorEx.class, "left");
        right = hardwareMap.get(DcMotorEx.class, "right");
        right.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        left.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        right.setDirection(DcMotorEx.Direction.REVERSE);

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

    }

    public void edgeDetector() throws RobotCoreException {
        previousGamepad.copy(currentGamepad);
        currentGamepad.copy(gamepad1);
    }
}
