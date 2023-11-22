package org.firstinspires.ftc.teamcode.Disabled;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
@Disabled
@TeleOp(name = "ServoT")
public class Mason extends LinearOpMode {
    Servo frogo;
    Gamepad currentGamepad = new Gamepad();
    Gamepad previousGamepad = new Gamepad();
    double servoPos;
    @Override
    public void runOpMode() throws InterruptedException {
        frogo = hardwareMap.get(Servo.class, "frogo");
        //Start pos: 0.85
        waitForStart();
        while (opModeIsActive()){
            try {
                edgeDetector();
            } catch (RobotCoreException e) {
                e.printStackTrace();
            }
            if (currentGamepad.a && !previousGamepad.a){ //Change to A once I get back
                servoPos = 0.85;
                frogo.setPosition(servoPos);
            }
            if (currentGamepad.b && !previousGamepad.b){ //Change to A once I get back
                servoPos = 0.05;
                frogo.setPosition(servoPos);
            }
        }
    }

    public void edgeDetector() throws RobotCoreException {
        previousGamepad.copy(currentGamepad);
        currentGamepad.copy(gamepad2);
    }
}
