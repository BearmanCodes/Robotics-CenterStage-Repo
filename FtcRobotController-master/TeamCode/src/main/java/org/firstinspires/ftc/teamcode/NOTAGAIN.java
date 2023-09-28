package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
@TeleOp(name = "NOTAGAIN")
public class NOTAGAIN extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotorEx bee = hardwareMap.get(DcMotorEx.class, "froggy");
        waitForStart();
        while (opModeIsActive()){
            bee.setPower(1);
        }
    }
}
