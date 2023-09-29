package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
@Disabled
@TeleOp(name = "Color")
public class Color extends LinearOpMode {
    ColorSensor colorSensor;
    DcMotorEx froggy;
    int red;
    int green;
    int blue;


    @Override
    public void runOpMode(){
        colorSensor = hardwareMap.get(ColorSensor.class, "color");
        froggy = hardwareMap.get(DcMotorEx.class, "froggy");
        froggy.setDirection(DcMotorSimple.Direction.FORWARD);
        colorSensor.enableLed(true);
        colorLoad();

        waitForStart();

        while (opModeIsActive()){
            colorLoad();
            if (red > green && red > blue){
                froggy.setPower(0.5);
            }
            if (green > red && green > blue){
                froggy.setPower(-0.5);
            }
            if (blue > red && blue > green){
                froggy.setPower(0);
            }
        }
    }

    public void colorLoad(){
        red = colorSensor.red();
        green = colorSensor.green();
        blue = colorSensor.blue();
    }

    public void telemetryColor(){
        telemetry.addData("Red: ", red);
        telemetry.addData("Green: ", green);
        telemetry.addData("Blue: ", blue);
        telemetry.update();
    }
}
