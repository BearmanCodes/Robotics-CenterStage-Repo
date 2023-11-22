package org.firstinspires.ftc.teamcode.Op;

import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;

import org.firstinspires.ftc.teamcode.R;

@TeleOp
public class MMMMMMasonDrive extends LinearOpMode {
    DrivetrainCore dTrain = new DrivetrainCore();
    ArmCore armCore = new ArmCore();
    ServoCore servoCore = new ServoCore();

    @Override
    public void runOpMode() throws InterruptedException {
        Init();
        waitForStart();
        while (opModeIsActive()) {
            try {
                servoCore.edgeDetector(gamepad1, gamepad2);
            } catch (RobotCoreException e) {
                throw new RuntimeException(e);
            }
            dTrain.run(gamepad1);
            armCore.trigger(gamepad2);
            servoCore.toggleRun();
        }
    }

    private void Init(){
        dTrain.init(hardwareMap);
        armCore.init(hardwareMap);
        servoCore.init(hardwareMap);

        SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, R.raw.sound);
    }
}
