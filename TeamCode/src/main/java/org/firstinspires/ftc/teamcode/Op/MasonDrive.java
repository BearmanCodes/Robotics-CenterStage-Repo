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
            if (servoCore.time.time() >= 90 && !servoCore.launched){ //Make sure you can't launch it before endgame
                servoCore.airLaunch(telemetry);
            }
            if (servoCore.currentGamepad.y && !servoCore.previousGamepad.y){
                dTrain.Flip(opModeIsActive(), telemetry);
            }
            dTrain.run(gamepad1);
            dTrain.tPully(gamepad1);
            armCore.rStick(gamepad2, telemetry);
            armCore.Arm(gamepad2);
            servoCore.dpadRun();
            telemetry.addData("Mason is: ", "stinky");
            telemetry.update();
        }
    }

    private void Init(){
        dTrain.init(hardwareMap);
        armCore.init(hardwareMap);
        servoCore.init(hardwareMap);
    }
}
