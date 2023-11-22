package org.firstinspires.ftc.teamcode.Op;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ArmCore {

    public DcMotorEx armMotor;
    public double reducerArm = 0.25;
    public double armPower;

    public void init(HardwareMap hwMap){
        armMotor = hwMap.get(DcMotorEx.class, "left".toLowerCase());

        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void trigger(Gamepad gamepad2){
        armPower = ((gamepad2.right_trigger + -gamepad2.left_trigger) * reducerArm) - 0.0025;
        armMotor.setPower(armPower);
    }

    public void rStick(Gamepad gamepad2){
        armPower = ((gamepad2.right_stick_y) * reducerArm) - 0.0025;
        armMotor.setPower(armPower);
    }

    public void lStick(Gamepad gamepad2){
        armPower = ((gamepad2.left_stick_y) * reducerArm) - 0.0025;
        armMotor.setPower(armPower);
    }
}
