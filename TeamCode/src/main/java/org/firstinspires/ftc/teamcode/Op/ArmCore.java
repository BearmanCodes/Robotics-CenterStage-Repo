package org.firstinspires.ftc.teamcode.Op;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ArmCore {

    public DcMotorEx armMotor;
    public double reducerArm = 0.25; //Change this depending on how much you want to reduce your arm
    public double armPower;

    public void init(HardwareMap hwMap){
        armMotor = hwMap.get(DcMotorEx.class, "left".toLowerCase()); //Change the string to name in config

        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    //This uses the triggers to move the arm as used in Mason M.'s op mode
    public void trigger(Gamepad gamepad2){
        armPower = ((gamepad2.right_trigger + -gamepad2.left_trigger) * reducerArm) - 0.0025; //the -0.0025 counteracts gravity
        armMotor.setPower(armPower);
    }

    //This uses the right stick to move the arm as used in Mason S.'s op mode
    public void rStick(Gamepad gamepad2){
        armPower = ((gamepad2.right_stick_y) * reducerArm) - 0.0025;
        armMotor.setPower(armPower);
    }

    //This uses the left stick to move the arm as used in Joel's op mode
    public void lStick(Gamepad gamepad2){
        armPower = ((gamepad2.left_stick_y) * reducerArm) - 0.0025;
        armMotor.setPower(armPower);
    }
}
