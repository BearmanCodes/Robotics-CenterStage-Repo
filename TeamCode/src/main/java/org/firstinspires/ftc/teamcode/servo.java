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
    Servo servo;
    Gamepad currentGamepad = new Gamepad();
    Gamepad previousGamepad = new Gamepad();
    double servoPos;
    @Override
    public void runOpMode() throws InterruptedException {
        servo = hardwareMap.get(Servo.class, "SERVORIGHT".toLowerCase());
        waitForStart();
        while (opModeIsActive()){
            try {
                edgeDetector();
            } catch (RobotCoreException e) {
                throw new RuntimeException(e);
            }
            if (currentGamepad.cross && !previousGamepad.cross){
                servoPos+=0.1;
                telemetry.addData("Servo Position", servoPos);
                telemetry.addData("current Servo Position", servo.getPosition());
                telemetry.update();
            }
            if (currentGamepad.circle && !previousGamepad.circle) {
                servoPos -= 0.1;
                telemetry.addData("Servo Position", servoPos);
                telemetry.addData("current Servo Position", servo.getPosition());
                telemetry.update();
            }
            if (currentGamepad.triangle && !previousGamepad.triangle) {
                servo.setPosition(servoPos);
                telemetry.addData("Servo Position", servoPos);
                telemetry.addData("current Servo Position", servo.getPosition());
                telemetry.update();
            }
        }

    }

    public void edgeDetector() throws RobotCoreException {
        previousGamepad.copy(currentGamepad);
        currentGamepad.copy(gamepad1);
    }
}
