package org.firstinspires.ftc.teamcode;

import android.annotation.TargetApi;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "RumbleTest")
public class Rumble extends LinearOpMode {

    Gamepad currentGamepad = new Gamepad();
    Gamepad previousGamepad = new Gamepad();

    boolean canRumble = true;

    Gamepad.RumbleEffect effect;

    ElapsedTime time = new ElapsedTime();
    @Override
    public void runOpMode() throws InterruptedException {
        Init();
        waitForStart();
        while (opModeIsActive()){
            try {
                edgeDetector();
            } catch (RobotCoreException e) {
                throw new RuntimeException(e);
            }
            if (gamepad1.left_bumper){
                gamepad1.rumble(gamepad1.left_trigger, gamepad1.right_trigger, Gamepad.RUMBLE_DURATION_CONTINUOUS);
            }
            if ((time.seconds() > 15.0) & canRumble){
                if (!gamepad1.isRumbling()){
                    gamepad1.rumbleBlips(5);
                }
                canRumble = false;
            }
        }
    }

    private void Init() {
        effect = new Gamepad.RumbleEffect.Builder()
                .build();
        time.reset();
    }

    public void edgeDetector() throws RobotCoreException {
        previousGamepad.copy(currentGamepad);
        currentGamepad.copy(gamepad1);
    }

}
