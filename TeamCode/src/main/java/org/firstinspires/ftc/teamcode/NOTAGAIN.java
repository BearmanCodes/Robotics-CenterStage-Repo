package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "motorTest")
public class NOTAGAIN extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotorEx bee = hardwareMap.get(DcMotorEx.class, "motorTest");
        bee.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();
        while (opModeIsActive()) {
            bee.setVelocity(-375);
        }
    }
}
