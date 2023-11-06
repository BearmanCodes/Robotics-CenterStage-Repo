package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

@TeleOp
public class ShawnDrive extends LinearOpMode {
    DcMotorEx frontleft, frontright, backleft, backright, armMotor;

    Servo lClaw, rClaw;

    IMU imu;
    IMU.Parameters imuparams;

    YawPitchRollAngles robotOrientation;

    AngularVelocity myRobotAngularVelocity;


    static final double TicksPerRev = 560;
    static final double WheelInches = (75 / 25.4);
    static final double TicksPerIn = TicksPerRev / (WheelInches * Math.PI);

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
        if(currentGamepad2.a && !previousGamepad2.a){
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

    public void main(){
        double Vertical = gamepad1.left_stick_y;
        double Horizontal = gamepad1.left_stick_x * 1.2;
        double Pivot = gamepad1.right_stick_x;

        double frontleftPower = (-Pivot + (Vertical - Horizontal)) * reducer;
        double frontrightPower = (Pivot + Vertical + Horizontal) * reducer;
        double backrightPower = (Pivot + (Vertical - Horizontal)) * reducer;
        double backleftPower = (-Pivot + Vertical + Horizontal) * reducer;

        if (currentGamepad.right_bumper && !previousGamepad.right_bumper){
            Turn();
        }

        /*Brake code Klarissa added*/  while (gamepad1.circle){
            allMotorPower(0);

            //make go back

        }
        
        frontleft.setPower(frontleftPower);
        frontright.setPower(frontrightPower);
        backleft.setPower(backleftPower);
        backright.setPower(backrightPower);

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

        imu = hardwareMap.get(IMU.class, "botIMU");
        imuparams = new IMU.Parameters(new RevHubOrientationOnRobot
                (RevHubOrientationOnRobot.LogoFacingDirection.UP,
                        RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD));

        imu.initialize(imuparams);
        imu.resetYaw();
        
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

    public void Turn() {
        if (opModeIsActive()) {
            robotOrientation = imu.getRobotYawPitchRollAngles();
            double Yaw = robotOrientation.getYaw(AngleUnit.DEGREES);
            double targetPos = Yaw - 180;
            if (opModeIsActive()) {
                while (Yaw > targetPos) {
                    setMotorVelocity(300, -300, 300, -300);
                    robotOrientation = imu.getRobotYawPitchRollAngles();
                    Yaw = robotOrientation.getYaw(AngleUnit.DEGREES);
                    telemetry.addData("Yaw:", "%.2f", Yaw);
                    telemetry.update();
                }
                allMotorVelocity(0);
                if (Yaw < targetPos) {
                    robotOrientation = imu.getRobotYawPitchRollAngles();
                    Yaw = robotOrientation.getYaw(AngleUnit.DEGREES);
                    double error = Yaw - -90;
                    while (Math.abs(error) > 0.2) {
                        setMotorVelocity(-300, 300, -300, 300);
                        robotOrientation = imu.getRobotYawPitchRollAngles();
                        Yaw = robotOrientation.getYaw(AngleUnit.DEGREES);
                        error = Yaw - 90;
                        telemetry.addData("Error: ", "%.2f", error);
                        telemetry.addData("Yaw:", "%.2f", Yaw);
                        telemetry.update();
                    }
                    allMotorVelocity(0);
                }
            }
        }
    }

    public void allMotorMode(DcMotor.RunMode mode) {
        frontleft.setMode(mode);
        frontright.setMode(mode);
        backleft.setMode(mode);
        backright.setMode(mode);
    }

    public void allMotorVelocity(double velocity) {
        frontleft.setVelocity(velocity);
        frontright.setVelocity(velocity);
        backleft.setVelocity(velocity);
        backright.setVelocity(velocity);
    }

    public void setMotorVelocity(double fLvelocity, double fRvelocity, double bLvelocity, double bRvelocity) {
        frontleft.setVelocity(fLvelocity);
        frontright.setVelocity(fRvelocity);
        backleft.setVelocity(bLvelocity);
        backright.setVelocity(bRvelocity);
    }

    public void allMotorPower(double power){
        frontleft.setPower(power);
        frontright.setPower(power);
        backleft.setPower(power);
        backright.setPower(power);
    }

    public void setMotorPower(double frontleftPower, double frontrightPower, double backleftPower,
                              double backrightPower){
        frontleft.setPower(frontleftPower);
        frontright.setPower(frontrightPower);
        backleft.setPower(backleftPower);
        backright.setPower(backrightPower);
    }

    public void allTargetPosition(int frontleftPos, int frontrightPos,
                                  int backleftPos, int backrightPos){
        frontleft.setTargetPosition(frontleftPos);
        frontright.setTargetPosition(frontrightPos);
        backleft.setTargetPosition(backleftPos);
        backright.setTargetPosition(backrightPos);
    }

    public void edgeDetector() throws RobotCoreException {
        previousGamepad.copy(currentGamepad);
        currentGamepad.copy(gamepad1);
        previousGamepad2.copy(currentGamepad2);
        currentGamepad2.copy(gamepad2);
    }
}
