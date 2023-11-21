import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.hardware.bosch.BNO055IMUNew;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.R;

@TeleOp(name = "yippie")
public class YIPPIE extends LinearOpMode {
    Gamepad previousGamepad = new Gamepad();
    Gamepad currentGamepad = new Gamepad();

    @Override
    public void runOpMode() throws InterruptedException {
        int soundID = hardwareMap.appContext.getResources().getIdentifier("sound.wav", "raw", hardwareMap.appContext.getPackageName());
        waitForStart();
        while (opModeIsActive()){
            try {
                edgeDetector();
            } catch (RobotCoreException e) {
                throw new RuntimeException(e);
            }
            if (currentGamepad.y && !previousGamepad.y){
                SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, R.raw.sound);
            }
        }
    }

    public void edgeDetector() throws RobotCoreException {
        previousGamepad.copy(currentGamepad);
        currentGamepad.copy(gamepad1);
    }
}
