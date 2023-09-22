package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name="ShawnDrive")
public class ShawnDrive extends LinearOpMode {
    DcMotorEx frontleft;
    DcMotorEx frontright;
    DcMotorEx backleft;
    DcMotorEx backright;

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();
        while (opModeIsActive()){
            frontright.setPower(1.0);
            frontleft.setPower(1.0);
            backleft.setPower(1.0);
            backright.setPower(1.0);
        }
    }

    private void initialize(){
        frontleft = hardwareMap.get(DcMotorEx.class, "frontleft");
        frontright = hardwareMap.get(DcMotorEx.class, "frontright");
        backleft = hardwareMap.get(DcMotorEx.class, "backleft");
        backright = hardwareMap.get(DcMotorEx.class, "backright");

        frontright.setDirection(DcMotorSimple.Direction.REVERSE);
        frontleft.setDirection(DcMotorSimple.Direction.FORWARD);
        backright.setDirection(DcMotorSimple.Direction.REVERSE);
        backleft.setDirection(DcMotorSimple.Direction.REVERSE);

    }
}
