package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
@Disabled
@TeleOp
public class Arm extends LinearOpMode {
    private DcMotorEx left, right;

    public double reducer = 0.8;
    public double armPower;
    @Override
    public void runOpMode() throws InterruptedException {
        initalize();
        waitForStart();
        while (opModeIsActive()){
            armPower = (-gamepad2.left_trigger + gamepad2.right_trigger) * reducer;
            left.setPower(armPower);
            right.setPower(armPower);
        }
    }
    public void initalize(){
        left = hardwareMap.get(DcMotorEx.class, "left");
        right = hardwareMap.get(DcMotorEx.class, "right");
        right.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        left.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        right.setDirection(DcMotorEx.Direction.REVERSE);
    }
}
