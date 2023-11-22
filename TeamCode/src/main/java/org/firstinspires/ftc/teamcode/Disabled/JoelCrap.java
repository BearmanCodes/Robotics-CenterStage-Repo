package org.firstinspires.ftc.teamcode.Disabled;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
@Disabled

@TeleOp(name = "Joel Spin")
public class JoelCrap extends LinearOpMode {
    DcMotorEx lMotor, rMotor;
    double mPower = 1.0;

    Gamepad currentGamepad = new Gamepad();
    Gamepad previousGamepad = new Gamepad();

    @Override
    public void runOpMode() throws InterruptedException {
        lMotor = hardwareMap.get(DcMotorEx.class, "lmotor");
        rMotor = hardwareMap.get(DcMotorEx.class, "rmotor");
        lMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();
        while (opModeIsActive()){
            try {
                edgeDetector();
            } catch (RobotCoreException e){
                throw new RuntimeException(e);
            }
            if (currentGamepad.dpad_up && !previousGamepad.dpad_up){
                mPower += 0.5;
            }
            if (currentGamepad.dpad_down && !previousGamepad.dpad_down){
                mPower -= 0.5;
            }
            lMotor.setPower(mPower);
            rMotor.setPower(mPower);
        }
    }

    public void edgeDetector() throws RobotCoreException{
        previousGamepad.copy(currentGamepad);
        currentGamepad.copy(gamepad1);
    }
}
