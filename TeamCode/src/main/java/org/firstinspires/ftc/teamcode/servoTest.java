package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.Gamepad;
import java.text.DecimalFormat;

import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "ServoTest")
public class servoTest extends LinearOpMode {
    Servo lservo, rservo;
    Gamepad currentGamepad =  new Gamepad();
    Gamepad previousGamepad = new Gamepad();

    private static final DecimalFormat dformat = new DecimalFormat("0.00");
    double lservoPos, rservoPos;
    @Override
    public void runOpMode() throws InterruptedException {
        lservo = hardwareMap.get(Servo.class, "lclaw".toLowerCase());
        rservo = hardwareMap.get(Servo.class, "rclaw".toLowerCase());
        //rservo init open 0.12
        //lservo init open 0.23
        //rservo init close 0.25
        //lservo init close 0.10
        lservo.setDirection(Servo.Direction.FORWARD);
        rservo.setDirection(Servo.Direction.FORWARD);
        waitForStart();
        while (opModeIsActive()){
            try {
                edgeDetector();
            } catch (RobotCoreException e) {
                throw new RuntimeException(e);
            }
            if (currentGamepad.a && !previousGamepad.a){
                lservoPos += 0.01;
                telemetryUpdate();
            }
            if (currentGamepad.b && !previousGamepad.b) {
                lservoPos -= 0.01;
                telemetryUpdate();
            }
            if (currentGamepad.y && !previousGamepad.y) {
                lservo.setPosition(lservoPos);
                rservo.setPosition(rservoPos);
                telemetryUpdate();
            }
            if (currentGamepad.left_bumper && !previousGamepad.left_bumper){
                rservoPos += 0.01;
                telemetryUpdate();
            }
            if (currentGamepad.right_bumper && !previousGamepad.right_bumper) {
                rservoPos -= 0.01;
                telemetryUpdate();
            }
        }
    }

    public void telemetryUpdate(){
        telemetry.addData("Left Servo Position", dformat.format(lservoPos));
        telemetry.addData("Current Left Servo Position", dformat.format(lservo.getPosition()));
        telemetry.addData("Right Servo Position", dformat.format(rservoPos));
        telemetry.addData("Current Right Servo Position", dformat.format(rservo.getPosition()));
        telemetry.update();
    }

    public void edgeDetector() throws RobotCoreException {
        previousGamepad.copy(currentGamepad);
        currentGamepad.copy(gamepad2);
    }
}