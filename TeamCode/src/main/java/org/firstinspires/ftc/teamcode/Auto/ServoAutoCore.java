package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ServoAutoCore {

    public Servo lClaw, rClaw;

    public void init(HardwareMap hwMap){
        rClaw = hwMap.get(Servo.class, "rclaw".toLowerCase());
        lClaw = hwMap.get(Servo.class, "lclaw".toLowerCase());
        rClaw.setDirection(Servo.Direction.REVERSE);

        lClaw.setDirection(Servo.Direction.FORWARD);

        rClaw.setPosition(0.10);
        lClaw.setPosition(0.13);
    }
}
