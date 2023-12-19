package org.firstinspires.ftc.teamcode.Op;

import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.R;

@TeleOp
public class MasonDrive extends LinearOpMode {
    DrivetrainCore dTrain = new DrivetrainCore();
    ArmCore armCore = new ArmCore();
    ServoCore servoCore = new ServoCore();
    ElapsedTime time = new ElapsedTime(ElapsedTime.Resolution.SECONDS);

    @Override
    public void runOpMode() throws InterruptedException {
        Init();
        waitForStart();
        time.reset();
        while (opModeIsActive()) {
            try {
                servoCore.edgeDetector(gamepad1, gamepad2);
            } catch (RobotCoreException e) {
                throw new RuntimeException(e);
            }
            if (time.time() >= 10 && !servoCore.launched){
                telemetry.addData("Time: ", time.time());
                telemetry.update();
                servoCore.airLaunch();
                time.reset();
                while (time.time() <= 1.5){
                    telemetry.addData("Time: ", time.time());
                    telemetry.update();
                    dTrain.allMotorPower(-1);
                }
                dTrain.allMotorPower(0);
            }
            dTrain.run(gamepad1);
            armCore.rStick(gamepad2);
            armCore.Arm(gamepad2);
            servoCore.dpadRun();
            telemetry.addData("Time: ", time.time());
            telemetry.update();
        }
    }

    private void Init(){
        dTrain.init(hardwareMap);
        armCore.init(hardwareMap);
        servoCore.init(hardwareMap);
    }
}
