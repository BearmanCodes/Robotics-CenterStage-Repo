package org.firstinspires.ftc.teamcode.Op;

import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ServoCore {
    Gamepad currentGamepad = new Gamepad();
    Gamepad previousGamepad = new Gamepad();

    Gamepad currentGamepad2 = new Gamepad();
    Gamepad previousGamepad2 = new Gamepad();

    public Servo lClaw, rClaw, airplane;

    public DrivetrainCore dTrain = new DrivetrainCore();

    Boolean launched = false;
    boolean clawstat;

    ElapsedTime time = new ElapsedTime(ElapsedTime.Resolution.SECONDS);

    public void init(HardwareMap hwMap){
        rClaw = hwMap.get(Servo.class, "rclaw".toLowerCase());
        lClaw = hwMap.get(Servo.class, "lclaw".toLowerCase());
        airplane = hwMap.get(Servo.class, "air".toLowerCase());

        airplane.setDirection(Servo.Direction.REVERSE);
        rClaw.setDirection(Servo.Direction.REVERSE);

        rClaw.setPosition(0.10);
        lClaw.setPosition(0.13); //Starting position that grasps a pixel in the back
        airplane.setPosition(0.36);
    }

    //Dpad control used in Mason S.'s op mode
    public void dpadRun(){

        if (currentGamepad2.dpad_up && !previousGamepad2.dpad_up){
            lClaw.setPosition(0.96); //(close)
            rClaw.setPosition(1.00); //(close)
        }
        if (currentGamepad2.dpad_down && !previousGamepad2.dpad_down){
            lClaw.setPosition(0.8); //(open)
            rClaw.setPosition(0.8); //(open)
        }
    }

    public void airLaunch(Telemetry telemetry){
        if (currentGamepad.x && !previousGamepad.x){
            airplane.setPosition(0.55);
            launched = true;
        }
    }

    //Toggle controls used in Joel and Mason M.'s op modes

    public void toggleRun(){
        if(currentGamepad2.a && !previousGamepad2.a){
            clawstat = !clawstat;
            if(clawstat){
                lClaw.setPosition(0.8); //(open)
                rClaw.setPosition(0.8); //(open)
            } else {
                lClaw.setPosition(0.96); //(close)
                rClaw.setPosition(1.00); //(close)
            }
        }
    }

    public void edgeDetector(Gamepad gamepad1, Gamepad gamepad2) throws RobotCoreException {
        previousGamepad.copy(currentGamepad);
        currentGamepad.copy(gamepad1);
        previousGamepad2.copy(currentGamepad2);
        currentGamepad2.copy(gamepad2);
    }

}
