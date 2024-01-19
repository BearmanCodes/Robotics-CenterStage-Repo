package org.firstinspires.ftc.teamcode.Op;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

//This is the core arm class every single TeleOp uses to access functions pertaining to the arm
public class ArmCore {

    public DcMotorEx armMotor;
    public DcMotorEx armMotor2; //Declare the 2 arm motors, this one is the extender
    public double reducerArm = 0.5; //Change this depending on how much you want to reduce your arm
    public double armPower;
    public double armMotorPower;
    public double armMotorReducer = 1;

    public void init(HardwareMap hwMap){
        armMotor2 = hwMap.get(DcMotorEx.class, "Extend".toLowerCase()); //Servo for the Linear extender thing
        armMotor = hwMap.get(DcMotorEx.class, "left".toLowerCase()); //Change the string to name in config

        armMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    //This uses the triggers to move the arm as used in Mason M.'s op mode
    public void trigger(Gamepad gamepad2){
        armPower = ((gamepad2.right_trigger + -gamepad2.left_trigger) * reducerArm) - 0.00225; //the -0.0025 counteracts gravity
        armMotor.setPower(armPower);
    }

    //This uses the right stick to move the arm as used in Mason S.'s op mode
    public void rStick(Gamepad gamepad2, Telemetry telemetry){
        if (armMotor.getCurrentPosition() >= 415) armPower = ((gamepad2.right_stick_y) * reducerArm) - 0.00450;
        else if (armMotor.getCurrentPosition() <= 414) armPower = ((gamepad2.right_stick_y) * reducerArm) + 0.00050;
        armMotor.setPower(armPower);
        telemetry.addData("ArmPos: ", armMotor.getCurrentPosition());
        telemetry.update();
    }

    //This uses the left stick to move the arm as used in Joel's op mode
    public void lStick(Gamepad gamepad2){
        armPower = ((gamepad2.left_stick_y) * (reducerArm + 0.1)) - 0.0025;
        armMotor.setPower(armPower);
    }

    //Main extender arm function
    public void Arm(Gamepad gamepad2){
        armMotorPower = (gamepad2.left_trigger + -gamepad2.right_trigger) * armMotorReducer;
        armMotor2.setPower(armMotorPower);
    }
}