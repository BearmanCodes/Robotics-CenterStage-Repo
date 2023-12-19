package org.firstinspires.ftc.teamcode.Op;

import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Auto.DriveAutoCore;
import org.firstinspires.ftc.teamcode.R;

@TeleOp
public class MasonDrive extends LinearOpMode {
    DrivetrainCore dTrain = new DrivetrainCore();
    ArmCore armCore = new ArmCore();
    ServoCore servoCore = new ServoCore();

    @Override
    public void runOpMode() throws InterruptedException {
        Init();
        waitForStart();
        servoCore.time.reset();
        while (opModeIsActive()) {
            try {
                servoCore.edgeDetector(gamepad1, gamepad2);
            } catch (RobotCoreException e) {
                throw new RuntimeException(e);
            }
            if (servoCore.time.time() >= 90 && !servoCore.launched){
                telemetry.addData("Time: ", servoCore.time.time());
                telemetry.update();
                servoCore.airLaunch(telemetry);
                /*
                if (servoCore.launched) {
                    dAuto.revDrive(99999999, 4, opModeIsActive(), 0);
                    dAuto.fwdDrive(99999999, 1, opModeIsActive(), 0);
                }
                */
            }
            dTrain.run(gamepad1);
            armCore.rStick(gamepad2);
            armCore.Arm(gamepad2);
            servoCore.dpadRun();
            telemetry.addData("Time: ", servoCore.time.time());
            telemetry.update();
        }
    }

    private void Init(){
        dTrain.init(hardwareMap);
        armCore.init(hardwareMap);
        servoCore.init(hardwareMap);
    }
}
