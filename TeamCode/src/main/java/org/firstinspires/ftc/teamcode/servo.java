package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
@TeleOp(name = "ServoTest")
public class servo extends LinearOpMode {
    Servo frogo;
    Gamepad currentGamepad = new Gamepad();
    Gamepad previousGamepad = new Gamepad();
    double servoPos;
    @Override
    public void runOpMode() throws InterruptedException {
        frogo = hardwareMap.get(Servo.class, "servoTest");
        //Start pos: 0.85
        waitForStart();
        while (opModeIsActive()){
            try {
                edgeDetector();
            } catch (RobotCoreException e) {
                e.printStackTrace();
            }
            if (currentGamepad.a && !previousGamepad.a){ //Change to A once I get back
                servoPos += 0.01;
                telemetry.addData("servoPos: ", servoPos);
                telemetry.addData("currentPos: ", frogo.getPosition());
                telemetry.update();
            }
            if (currentGamepad.b && !previousGamepad.b){ //Change to A once I get back
                servoPos -= 0.01;
                telemetry.addData("servoPos: ", servoPos);
                telemetry.addData("currentPos: ", frogo.getPosition());
                telemetry.update();
            }
            if (currentGamepad.y && !previousGamepad.y){
                frogo.setPosition(servoPos);
                telemetry.addData("servoPos", servoPos);
                telemetry.addData("currentPos", frogo.getPosition());
                telemetry.update();
            }
        }
    }

    public void edgeDetector() throws RobotCoreException {
        previousGamepad.copy(currentGamepad);
        currentGamepad.copy(gamepad2);
    }
}
