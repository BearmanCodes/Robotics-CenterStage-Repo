package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.checkerframework.checker.units.qual.A;

@TeleOp(name = "ServoTest")
public class servo extends LinearOpMode {
    Servo lservo, rservo;
    Gamepad currentGamepad =  new Gamepad();
    Gamepad previousGamepad = new Gamepad();
    double lservoPos, rservoPos;
    @Override
    public void runOpMode() throws InterruptedException {
        lservo = hardwareMap.get(Servo.class, "leftclaw".toLowerCase());
        rservo = hardwareMap.get(Servo.class, "rightclaw".toLowerCase());
        waitForStart();
        while (opModeIsActive()){
            try {
                edgeDetector();
            } catch (RobotCoreException e) {
                throw new RuntimeException(e);
            }
            if (currentGamepad.a && !previousGamepad.a){
                lservoPos +=0.1;
                telemetry.addData("Left Servo Position", lservoPos);
                telemetry.addData("Current Left Servo Position", lservo.getPosition());
                telemetry.addData("Right Servo Position", rservoPos);
                telemetry.addData("Current Right Servo Position", rservo.getPosition());
                telemetry.update();
            }
            if (currentGamepad.b && !previousGamepad.b) {
                lservoPos -= 0.1;
                telemetry.addData("Left Servo Position", lservoPos);
                telemetry.addData("Current Left Servo Position", lservo.getPosition());
                telemetry.addData("Right Servo Position", rservoPos);
                telemetry.addData("Current Right Servo Position", rservo.getPosition());
                telemetry.update();
            }
            if (currentGamepad.y && !previousGamepad.y) {
                lservo.setPosition(lservoPos);
                rservo.setPosition(rservoPos);
                telemetry.addData("Left Servo Position", lservoPos);
                telemetry.addData("Current Left Servo Position", lservo.getPosition());
                telemetry.addData("Right Servo Position", rservoPos);
                telemetry.addData("Current Right Servo Position", rservo.getPosition());
                telemetry.update();
            }
            if (currentGamepad.left_bumper && !currentGamepad.left_bumper){
                rservoPos += 0.1;
                telemetry.addData("Left Servo Position", lservoPos);
                telemetry.addData("Current Left Servo Position", lservo.getPosition());
                telemetry.addData("Right Servo Position", rservoPos);
                telemetry.addData("Current Right Servo Position", rservo.getPosition());
                telemetry.update();
            }
            if (currentGamepad.right_bumper && !currentGamepad.right_bumper) {
                rservoPos -= 0.1;
                telemetry.addData("Left Servo Position", lservoPos);
                telemetry.addData("Current Left Servo Position", lservo.getPosition());
                telemetry.addData("Right Servo Position", rservoPos);
                telemetry.addData("Current Right Servo Position", rservo.getPosition());
                telemetry.update();
            }
        }

    }

    public void edgeDetector() throws RobotCoreException {
        previousGamepad.copy(currentGamepad);
        currentGamepad.copy(gamepad2);
    }
}
